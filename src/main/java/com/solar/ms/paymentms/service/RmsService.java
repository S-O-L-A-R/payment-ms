package com.solar.ms.paymentms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RmsService {

    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @Value("${service.rms-ms.check-status.url}")
    private String healthCheckUrl;

    public ResponseEntity<String> checkHealth(){
        return loadBalancedRestTemplate.exchange(
                healthCheckUrl,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );
    }
}
