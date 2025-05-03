package com.devpuccino.accountservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("app.account-auth-service.base-url")
    private String accountAuthServiceUrl;

    @Bean("account-auth-service-rest-template")
    public RestTemplate accountAuthServiceRestTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.rootUri(accountAuthServiceUrl).build();
    }
}
