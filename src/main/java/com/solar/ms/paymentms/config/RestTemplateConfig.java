package com.solar.ms.paymentms.config;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Configuration
public class RestTemplateConfig {

    private static final List<String> ALLOW_HEADER_LIST = Collections.unmodifiableList(Arrays.asList("correlationid", "userid", "accept-language", "loginid", "channelid", "x-forwarded-for", "scb-channel", "accessid", "api-scope"));

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
                if (ALLOW_HEADER_LIST.contains(header.toLowerCase())) {
                    log.info("Adding header {} with value {}", header, value);
                    httpHeaders.add(header, value);
                }
            }
        }

        if (!httpHeaders.containsKey("accept-language") || StringUtils.isEmpty(httpHeaders.getFirst("accept-language"))) {
            httpHeaders.set("accept-language", "th");
        }

        return httpHeaders;
    }
}
