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

import static com.devpuccino.accountservice.constant.ResponseConstant.SUCCESS_CODE;
import static com.devpuccino.accountservice.constant.ResponseConstant.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private static Logger logger = LogManager.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @GetMapping
    public CommonResponse<List<Wallet>> getAllWallet() {
        List<Wallet> wallets = walletService.getAllWallet();
        return CommonResponse.<List<Wallet>>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).data(wallets).build();
    }

    @PostMapping
    public CommonResponse<Wallet> createWallet(@RequestBody WalletRequest request) {
        Wallet wallet = walletService.insertWallet(request);
        return CommonResponse.<Wallet>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).data(wallet).build();
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteWallet(@PathVariable("id") String walletId) {
        walletService.deleteWallet(walletId);
        return CommonResponse.builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).build();
    }

    @GetMapping("/{id}")
    public CommonResponse<Wallet> getWalletById(@PathVariable("id") String walletId) throws DataNotFoundException {
        Wallet wallet = walletService.getWalletById(walletId);
        return CommonResponse.<Wallet>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).data(wallet).build();
    }

}
