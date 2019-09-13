package com.solar.ms.paymentms.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class RestTemplateConfig {
    @LoadBalanced
    @Bean(name = "loadBalancedRestTemplate")
    public RestTemplate getLoadBalancedRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Primary
    @Bean(name = "externalRestTemplate")
    public RestTemplate getExternalRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    @Bean
    @RequestScope
    public HttpHeaders httpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> headerNames = curRequest.getHeaderNames();
        if (headerNames != null) {
            while(headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                String value = curRequest.getHeader(header);
                httpHeaders.add(header, value);
            }
        }

        if (!httpHeaders.containsKey("accept-language") || StringUtils.isEmpty(httpHeaders.getFirst("accept-language"))) {
            httpHeaders.set("accept-language", "th");
        }

        return httpHeaders;
    }
}
