package com.devpuccino.accountservice.domain.request;


import java.math.BigDecimal;

public record WalletRequest(String walletName,
                            String walletType,
                            BigDecimal balance,
                            String currency,
                            BigDecimal creditLimit,
                            String cardType,
                            String paymentDueDate,
                            String billingDate
) {
    public WalletRequest(String walletName, String walletType, BigDecimal balance, String currency) {
        this(walletName, walletType, balance, currency, null, null, null, null);
    }
}
