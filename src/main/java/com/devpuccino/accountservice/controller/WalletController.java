package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.constant.ResponseConstant;
import com.devpuccino.accountservice.domain.request.WalletRequest;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping
    public CommonResponse<Wallet> createWallet(@RequestBody WalletRequest request){
        Wallet wallet = walletService.insertWallet(request);
        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(wallet);
        return response;
    }
}
