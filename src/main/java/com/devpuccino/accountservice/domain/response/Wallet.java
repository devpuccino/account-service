package com.devpuccino.accountservice.domain.response;

import java.math.BigDecimal;

public record Wallet(
        String walletId,
        String walletName,
        String walletType,
        BigDecimal balance,
        String currency,
        BigDecimal creditLimit,
        String cardType,
        String paymentDueDate,
        String billingDate) {
    public Wallet(String walletId, String walletName,String walletType,BigDecimal balance,String currency){
        this(walletId , walletName, walletType,balance,currency,null,null,null,null);
    }
}
