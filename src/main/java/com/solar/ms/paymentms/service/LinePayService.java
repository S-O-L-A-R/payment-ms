package com.solar.ms.paymentms.service;

import com.solar.ms.paymentms.model.linepay.LinePayConfirmRequest;
import com.solar.ms.paymentms.model.linepay.LinePayReserveRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static com.solar.ms.paymentms.config.CommonConstants.CURRENCY_THB;
import static com.solar.ms.paymentms.config.CommonConstants.LINE_PAY_CHANNEL_ID_HEADER_KEY;
import static com.solar.ms.paymentms.config.CommonConstants.LINE_PAY_CHANNEL_SECRET_HEADER_KEY;

@Slf4j
@Service
public class LinePayService {

    @Autowired
    private HttpHeaders httpHeaders;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${linepay.channel-id:default}")
    private String channelId;
    @Value("${linepay.channel-secret:default}")
    private String channelSecret;

    @Value("${service.linepay.reserve.url}")
    private String reserveUrl;
    @Value("${service.linepay.confirm.url}")
    private String confirmUrl;
    @Value("${service.payment.linepay.payment-callback.url}")
    private String callbackUrl;

    public ResponseEntity<String> reservePayment(){
        httpHeaders.set(LINE_PAY_CHANNEL_ID_HEADER_KEY, channelId);
        httpHeaders.set(LINE_PAY_CHANNEL_SECRET_HEADER_KEY, channelSecret);

        LinePayReserveRequest linePayReserveRequest = new LinePayReserveRequest();
        linePayReserveRequest.setProductName("Test");
        linePayReserveRequest.setAmount(BigDecimal.ONE);
        linePayReserveRequest.setOrderId("ORDER-" + UUID.randomUUID().toString());
        linePayReserveRequest.setCurrency(CURRENCY_THB);
        linePayReserveRequest.setLangCd(httpHeaders.getFirst(HttpHeaders.ACCEPT_LANGUAGE));
        linePayReserveRequest.setConfirmUrl(callbackUrl);
        linePayReserveRequest.setConfirmUrlType("SERVER");

        return restTemplate.exchange(
                reserveUrl,
                HttpMethod.POST,
                new HttpEntity<>(linePayReserveRequest, httpHeaders),
                String.class
        );
    }

    public ResponseEntity<String> confirmPayment(String transactionId, String orderId){
        httpHeaders.set(LINE_PAY_CHANNEL_ID_HEADER_KEY, channelId);
        httpHeaders.set(LINE_PAY_CHANNEL_SECRET_HEADER_KEY, channelSecret);

        LinePayConfirmRequest linePayConfirmRequest = new LinePayConfirmRequest();
        linePayConfirmRequest.setAmount(BigDecimal.ONE);
        linePayConfirmRequest.setCurrency(CURRENCY_THB);

        try {
            return restTemplate.exchange(
                    confirmUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(linePayConfirmRequest, httpHeaders),
                    String.class,
                    transactionId
            );
        }catch (Exception e){
            log.error("", e);
            throw e;
        }
    }
}
