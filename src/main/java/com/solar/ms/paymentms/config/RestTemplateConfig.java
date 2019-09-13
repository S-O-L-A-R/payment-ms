package com.solar.ms.paymentms.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @LoadBalanced
    @Bean(name = "loadBalancedRestTemplate")
    public RestTemplate getLoadBalancedRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    @Bean
    @Scope(value = "request")
    public HttpHeaders httpHeaders(){
        return new HttpHeaders();
    }
}
