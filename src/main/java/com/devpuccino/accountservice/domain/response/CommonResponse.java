package com.devpuccino.accountservice.domain.response;

import com.devpuccino.accountservice.constant.ResponseConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CommonResponse<T> {
    @Schema(title = "Response code",example = ResponseConstant.SUCCESS_CODE)
    private String code;
    @Schema(title = "Response message",example = ResponseConstant.SUCCESS_MESSAGE)
    private String message;
    private T data;
}
