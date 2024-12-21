package com.devpuccino.accountservice.service;

import com.devpuccino.accountservice.domain.request.WalletRequest;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.entity.WalletEntity;
import com.devpuccino.accountservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public Wallet insertWallet(WalletRequest request) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletName(request.walletName());
        walletEntity.setWalletType(request.walletType());
        walletEntity.setBalance(request.balance());
        walletEntity.setCurrency(request.currency());
        walletEntity.setCardType(request.cardType());
        walletEntity.setCreditLimit(request.creditLimit());
        walletEntity.setPaymentDueDate(request.paymentDueDate());
        walletEntity.setBillingDate(request.billingDate());

        walletEntity = walletRepository.save(walletEntity);

        Wallet wallet = new Wallet(
                walletEntity.getWalletId().toString(),
                walletEntity.getWalletName(),
                walletEntity.getWalletType(),
                walletEntity.getBalance(),
                walletEntity.getCurrency(),
                walletEntity.getCreditLimit(),
                walletEntity.getCardType(),
                walletEntity.getPaymentDueDate(),
                walletEntity.getBillingDate());
        return wallet;
    }
}
