package com.devpuccino.accountservice.exception;

import com.devpuccino.accountservice.domain.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleException(Exception exception){
        CommonResponse response = new CommonResponse();
        response.setCode("500-001");
        response.setMessage("Internal server error");
        return response;
    }

    @ExceptionHandler(DuplicateDataException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse handleDuplicateDataException(DuplicateDataException exception){
        CommonResponse response = new CommonResponse();
        response.setCode("400-001");
        response.setMessage("Duplicate data");
        return response;
    }
}
