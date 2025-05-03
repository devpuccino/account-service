package com.devpuccino.accountservice.domain.request;

import com.devpuccino.accountservice.constant.TransactionType;
import com.devpuccino.accountservice.entity.TransactionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public record TransactionRequest(
        @NotNull
        TransactionType transactionType,
        @NotBlank
        String walletId,
        @NotBlank
        String categoryId,
        @NotNull
        Date transactionDate,
        @NotNull
        BigDecimal amount
) {
 public TransactionEntity toTransactionEntity(){
         TransactionEntity entity = new TransactionEntity();
         entity.setTransactionType(transactionType);
         entity.setTransactionDate(transactionDate);
         entity.setTransactionAmount(amount);
         entity.setWalletId(Integer.parseInt(walletId));
         entity.setCategoryId(Integer.parseInt(categoryId));
         return entity;
 }
}
