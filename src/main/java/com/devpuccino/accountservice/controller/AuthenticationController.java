package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.constant.ResponseConstant;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    @Qualifier("account-auth-service-rest-template")
    private RestTemplate authServiceRestTemplate;

    @GetMapping
    public CommonResponse authentication() throws URISyntaxException {
        Map requestBody = new HashMap<String,String>();
        HttpEntity entity = new HttpEntity(requestBody);
        authServiceRestTemplate.exchange("http://account-auth-service:8080/account-auth-service/api/v1/authentication/login", HttpMethod.POST,entity,Map.class);

        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        return response;
    }


}
