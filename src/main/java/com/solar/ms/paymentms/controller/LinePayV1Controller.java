package com.solar.ms.paymentms.controller;

import com.solar.ms.paymentms.service.LinePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/linepay")
public class LinePayV1Controller {

    @Autowired
    private HttpHeaders httpHeaders;
    @Autowired
    private LinePayService linePayService;

    @PostMapping(value = "/reserve")
    public ResponseEntity<?> reservePayment(@RequestBody String requestBody){
        log.info("HEADER: {}, BODY: {}", httpHeaders, requestBody);

        return linePayService.reservePayment();
    }
}
