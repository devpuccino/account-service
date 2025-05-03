package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.domain.request.TransactionRequest;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.devpuccino.accountservice.domain.response.SaveTransactionResponse;
import com.devpuccino.accountservice.service.TransactionService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.devpuccino.accountservice.constant.ResponseConstant.SUCCESS_CODE;
import static com.devpuccino.accountservice.constant.ResponseConstant.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/transaction")
@Validated
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;
    @PostMapping
    public CommonResponse<SaveTransactionResponse> insertTransaction(@RequestBody @Valid TransactionRequest request) {
        Long transactionId = transactionService.insertTransaction(request);
        return CommonResponse.<SaveTransactionResponse>builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .data(new SaveTransactionResponse(transactionId))
                .build();
    }
}
