package com.devpuccino.accountservice.exception;

import com.devpuccino.accountservice.domain.response.CommonResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.devpuccino.accountservice.constant.ResponseConstant.*;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleException(Exception exception){
        logger.error(exception.getMessage(),exception);
        CommonResponse response = new CommonResponse();
        response.setCode(UNEXPECTED_ERROR_CODE);
        response.setMessage(UNEXPECTED_ERROR_MESSAGE);
        return response;
    }

    @ExceptionHandler(DuplicateDataException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse handleDuplicateDataException(DuplicateDataException exception){
        CommonResponse response = new CommonResponse();
        response.setCode(DUPLICATE_DATA_CODE);
        response.setMessage(DUPLICATE_DATA_MESSAGE);
        return response;
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public CommonResponse handleDataNotFoundException(DataNotFoundException exception){
        logger.error(exception.getMessage(),exception);
        CommonResponse response = new CommonResponse();
        response.setCode(DATA_NOT_FOUND_CODE);
        response.setMessage(DATA_NOT_FOUND_MESSAGE);
        return response;
    }
}
