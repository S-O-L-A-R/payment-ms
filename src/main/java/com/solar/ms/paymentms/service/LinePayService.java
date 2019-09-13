package com.solar.ms.paymentms.service;

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

    public ResponseEntity<String> reservePayment(){
        httpHeaders.set(LINE_PAY_CHANNEL_ID_HEADER_KEY, channelId);
        httpHeaders.set(LINE_PAY_CHANNEL_SECRET_HEADER_KEY, channelSecret);

        LinePayReserveRequest linePayReserveRequest = new LinePayReserveRequest();
        linePayReserveRequest.setProductName("test");
        linePayReserveRequest.setAmount(BigDecimal.ONE);
        linePayReserveRequest.setOrderId("ORDER-111");
        linePayReserveRequest.setCurrency(CURRENCY_THB);
        linePayReserveRequest.setLangCd("th");
        linePayReserveRequest.setConfirmUrl("https://www.google.com");
        linePayReserveRequest.setConfirmUrlType("SERVER");

        return restTemplate.exchange(
                reserveUrl,
                HttpMethod.POST,
                new HttpEntity<>(linePayReserveRequest, httpHeaders),
                String.class
        );
    }
}
