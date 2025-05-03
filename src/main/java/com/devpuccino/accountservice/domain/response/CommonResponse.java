package com.devpuccino.accountservice.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommonResponse<T> {
    private String code;
    private String message;
    private T data;
}
