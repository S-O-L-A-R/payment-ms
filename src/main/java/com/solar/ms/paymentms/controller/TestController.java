package com.solar.ms.paymentms.controller;

import com.solar.ms.paymentms.service.RmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RmsService rmsService;

    @GetMapping(value = "/rms/health")
    public ResponseEntity<String> checkRmsHealth(){
        return rmsService.checkHealth();
    }
}
