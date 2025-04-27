package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.constant.ResponseConstant;
import com.devpuccino.accountservice.domain.request.WalletRequest;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.exception.DataNotFoundException;
import com.devpuccino.accountservice.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private static Logger logger = LogManager.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;
    @GetMapping
    public CommonResponse<List<Wallet>> getAllWallet(){
        logger.info("getAllWallet");

        List<Wallet> wallets = walletService.getAllWallet();
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(ResponseConstant.SUCCESS_CODE);
        commonResponse.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        commonResponse.setData(wallets);

        return commonResponse;
    }

    @PostMapping
    public CommonResponse<Wallet> createWallet(@RequestBody WalletRequest request) {
        Wallet wallet = walletService.insertWallet(request);
        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(wallet);
        return response;
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteWallet(@PathVariable("id") String walletId) {
        walletService.deleteWallet(walletId);
        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        return response;
    }
    @GetMapping("/{id}")
    public CommonResponse<Wallet> getWalletById(@PathVariable("id") String walletId) throws DataNotFoundException {
        Wallet wallet = walletService.getWalletById(walletId);
        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(wallet);
        return response;
    }

}
