package com.devpuccino.accountservice.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class CommonResponse {
    private String code;
    private String message;
    private List<Category> data;
}
