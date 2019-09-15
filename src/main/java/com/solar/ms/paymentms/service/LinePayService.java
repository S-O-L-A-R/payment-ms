package com.solar.ms.paymentms.service;

import com.solar.ms.paymentms.model.ReservePaymentRequest;
import com.solar.ms.paymentms.model.linepay.LinePayConfirmRequest;
import com.solar.ms.paymentms.model.linepay.LinePayReserveRequest;
import com.solar.ms.paymentms.model.linepay.LinePayReserveResponse;
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

    public ResponseEntity<LinePayReserveResponse> reservePayment(ReservePaymentRequest reservePaymentRequest){
        httpHeaders.set(LINE_PAY_CHANNEL_ID_HEADER_KEY, channelId);
        httpHeaders.set(LINE_PAY_CHANNEL_SECRET_HEADER_KEY, channelSecret);

        LinePayReserveRequest linePayReserveRequest = new LinePayReserveRequest();
        linePayReserveRequest.setProductName(reservePaymentRequest.getProductName());
        linePayReserveRequest.setAmount(reservePaymentRequest.getAmount());
        linePayReserveRequest.setOrderId(reservePaymentRequest.getOrderId());
        linePayReserveRequest.setCurrency(CURRENCY_THB);
        linePayReserveRequest.setLangCd(httpHeaders.getFirst(HttpHeaders.ACCEPT_LANGUAGE));
        linePayReserveRequest.setConfirmUrl(reservePaymentRequest.getConfirmUrl());
        linePayReserveRequest.setConfirmUrlType("SERVER");
        linePayReserveRequest.setProductImageUrl("https://avatars2.githubusercontent.com/u/54903129?s=200&v=4");

        return restTemplate.exchange(
                reserveUrl,
                HttpMethod.POST,
                new HttpEntity<>(linePayReserveRequest, httpHeaders),
                LinePayReserveResponse.class
        );
    }

    public ResponseEntity<String> confirmPayment(String transactionId, BigDecimal amount){
        httpHeaders.set(LINE_PAY_CHANNEL_ID_HEADER_KEY, channelId);
        httpHeaders.set(LINE_PAY_CHANNEL_SECRET_HEADER_KEY, channelSecret);

        LinePayConfirmRequest linePayConfirmRequest = new LinePayConfirmRequest();
        linePayConfirmRequest.setAmount(amount);
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
