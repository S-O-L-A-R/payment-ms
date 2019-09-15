package com.solar.ms.paymentms.controller;

import com.solar.ms.paymentms.service.RmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private RmsService rmsService;

    @Value("${linepay.channel-id:default}")
    public String channelId;
    @Value("${linepay.channel-secret:default}")
    public String channelSecret;

    @GetMapping(value = "/rms/health")
    public ResponseEntity<String> checkRmsHealth(){
        return rmsService.checkHealth();
    }
}
