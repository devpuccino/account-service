package com.devpuccino.accountservice.exception;

import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return CommonResponse.builder().code(UNEXPECTED_ERROR_CODE).message(UNEXPECTED_ERROR_MESSAGE).build();
    }

    @ExceptionHandler(DuplicateDataException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse handleDuplicateDataException(DuplicateDataException exception){
        logger.error(exception.getMessage(),exception);
        return CommonResponse.builder().code(DUPLICATE_DATA_CODE).message(DUPLICATE_DATA_MESSAGE).build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public CommonResponse handleDataNotFoundException(DataNotFoundException exception){
        logger.error(exception.getMessage(),exception);
        return CommonResponse.builder().code(DATA_NOT_FOUND_CODE).message(DATA_NOT_FOUND_MESSAGE).build();
    }
    @ExceptionHandler(value={MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse handleMethodArgumentNotValidException(Exception exception){
        logger.error(exception.getMessage(),exception);
        return CommonResponse.builder().code(INVALID_REQUEST_CODE).message(INVALID_REQUEST_MESSAGE).build();
    }
}
