package com.devpuccino.accountservice.service;

import com.devpuccino.accountservice.domain.request.WalletRequest;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.entity.WalletEntity;
import com.devpuccino.accountservice.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Test
    public void shouldReturnWallet_WhenInsertCashWallet() {
        WalletRequest request = new WalletRequest("Travel", "cash", BigDecimal.valueOf(10000), "bath");

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(1);
        walletEntity.setWalletName(request.walletName());
        walletEntity.setCurrency(request.currency());
        walletEntity.setWalletType(request.walletType());
        walletEntity.setBalance(request.balance());

        Mockito.when(walletRepository.save(Mockito.any(WalletEntity.class))).thenReturn(walletEntity);

        Wallet actual = walletService.insertWallet(request);
        Assertions.assertEquals("1", actual.walletId());
        Assertions.assertEquals(request.walletName(), actual.walletName());
        Assertions.assertEquals(request.walletType(), actual.walletType());
        Assertions.assertEquals(request.balance(), actual.balance());
        Assertions.assertEquals(request.currency(), actual.currency());
    }

    @Test
    public void shouldReturnWallet_WhenInsertCreditCardWallet() {
        WalletRequest request = new WalletRequest("Travel",
                "cash",
                null,
                "bath",
                BigDecimal.valueOf(40000),
                "visa",
                "1",
                "30"
        );
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(1);
        walletEntity.setWalletName(request.walletName());
        walletEntity.setCurrency(request.currency());
        walletEntity.setWalletType(request.walletType());
        walletEntity.setCardType(request.cardType());
        walletEntity.setCreditLimit(request.creditLimit());
        walletEntity.setPaymentDueDate(request.paymentDueDate());
        walletEntity.setBillingDate(request.billingDate());

        Mockito.when(walletRepository.save(Mockito.any(WalletEntity.class))).thenReturn(walletEntity);
        Wallet actual = walletService.insertWallet(request);
        Assertions.assertEquals("1", actual.walletId());
        Assertions.assertEquals(request.walletName(), actual.walletName());
        Assertions.assertEquals(request.walletType(), actual.walletType());
        Assertions.assertEquals(request.balance(), actual.balance());
        Assertions.assertEquals(request.currency(), actual.currency());
        Assertions.assertEquals(request.creditLimit(), actual.creditLimit());
        Assertions.assertEquals(request.cardType(), actual.cardType());
        Assertions.assertEquals(request.paymentDueDate(), actual.paymentDueDate());
        Assertions.assertEquals(request.billingDate(), actual.billingDate());
    }
}
