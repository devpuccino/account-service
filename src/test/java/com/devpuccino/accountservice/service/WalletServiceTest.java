package com.devpuccino.accountservice.service;

import com.devpuccino.accountservice.constant.TransactionType;
import com.devpuccino.accountservice.constant.WalletType;
import com.devpuccino.accountservice.domain.request.WalletRequest;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.entity.TransactionEntity;
import com.devpuccino.accountservice.entity.WalletEntity;
import com.devpuccino.accountservice.exception.DataNotFoundException;
import com.devpuccino.accountservice.repository.TransactionRepository;
import com.devpuccino.accountservice.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void shouldReturnWallet_WhenInsertCashWallet() {
        WalletRequest request = new WalletRequest("Travel", "cash", BigDecimal.valueOf(10000), "bath");

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(1);
        walletEntity.setWalletName(request.walletName());
        walletEntity.setCurrency(request.currency());
        walletEntity.setWalletType(request.walletType());

        Mockito.when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        Wallet actual = walletService.insertWallet(request);
        Assertions.assertEquals("1", actual.walletId());
        Assertions.assertEquals(request.walletName(), actual.walletName());
        Assertions.assertEquals(request.walletType(), actual.walletType());
        Assertions.assertEquals(request.balance(), actual.balance());
        Assertions.assertEquals(request.currency(), actual.currency());
        Mockito.verify(transactionRepository,times(1)).save(any());
    }

    @Test
    public void shouldReturnWallet_WhenInsertCreditCardWallet() {
        WalletRequest request = new WalletRequest("Travel",
                "credit_card",
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

        Mockito.when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);
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
        Mockito.verify(transactionRepository,times(0)).save(any());
    }

    @Test
    public void shouldSuccess_whenDeleteWallet() {
        Assertions.assertDoesNotThrow(() -> {
            walletService.deleteWallet("1");
            Mockito.verify(walletRepository, times(1)).delete(any());
        });

    }
    @Test
    public void shouldReturnWalletCreditCard_whenGetWalletById() throws DataNotFoundException {
        Wallet expected = new Wallet("1", "Travel", "credit_card", null, "bath", BigDecimal.valueOf(40000), "visa", "1", "30");
        WalletEntity entity = new WalletEntity();
        entity.setWalletId(Integer.parseInt(expected.walletId()));
        entity.setWalletName(expected.walletName());
        entity.setWalletType(expected.walletType());
        entity.setCurrency(expected.currency());
        entity.setCreditLimit(expected.creditLimit());
        entity.setCardType(expected.cardType());
        entity.setBillingDate(expected.billingDate());
        entity.setPaymentDueDate(expected.paymentDueDate());

        Mockito.when(walletRepository.findById(any())).thenReturn(Optional.of(entity));
        Wallet actual = walletService.getWalletById("1");
        Assertions.assertEquals(expected,actual);
        Mockito.verify(transactionRepository,times(0)).findAllByWalletId(Integer.parseInt(expected.walletId()));
    }
    @Test
    public void shouldReturnWalletCash_whenGetWalletById() throws DataNotFoundException {
        Wallet expected = new Wallet("1", "Travel", WalletType.CASH, BigDecimal.valueOf(1000), "bath");

        WalletEntity entity = new WalletEntity();
        entity.setWalletId(Integer.parseInt(expected.walletId()));
        entity.setWalletName(expected.walletName());
        entity.setWalletType(expected.walletType());
        entity.setCurrency(expected.currency());

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionAmount(expected.balance());
        transactionEntity.setTransactionType(TransactionType.INITIAL);

        Mockito.when(walletRepository.findById(any())).thenReturn(Optional.of(entity));
        Mockito.when(transactionRepository.findAllByWalletId(anyInt())).thenReturn(Arrays.asList(transactionEntity));

        Wallet actual = walletService.getWalletById("1");
        Assertions.assertEquals(expected,actual);
        Mockito.verify(transactionRepository,times(1)).findAllByWalletId(Integer.parseInt(expected.walletId()));
    }
    @Test
    public void shouldThrowDataNotFoundException_whenGetWalletByInvalidId() {
        Mockito.when(walletRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class,()->{
            walletService.getWalletById("1");
        });
    }

    @Test
    public void shouldReturnWalletList_WhenGetAllWallet(){
        Wallet expectedWallet1 = new Wallet("1", "Travel", "credit_card", null, "bath", BigDecimal.valueOf(40000), "visa", "1", "30");
        Wallet expectedWallet2 = new Wallet("2", "Saving", "cash", BigDecimal.valueOf(1000), "bath");
        WalletEntity entity1 = new WalletEntity();
        entity1.setWalletId(Integer.parseInt(expectedWallet1.walletId()));
        entity1.setWalletName(expectedWallet1.walletName());
        entity1.setWalletType(expectedWallet1.walletType());
        entity1.setCurrency(expectedWallet1.currency());
        entity1.setCreditLimit(expectedWallet1.creditLimit());
        entity1.setCardType(expectedWallet1.cardType());
        entity1.setBillingDate(expectedWallet1.billingDate());
        entity1.setPaymentDueDate(expectedWallet1.paymentDueDate());

        WalletEntity entity2 = new WalletEntity();
        entity2.setWalletId(Integer.parseInt(expectedWallet2.walletId()));
        entity2.setWalletName(expectedWallet2.walletName());
        entity2.setWalletType(expectedWallet2.walletType());
        entity2.setCurrency(expectedWallet2.currency());

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(TransactionType.INITIAL);
        transactionEntity.setTransactionAmount(expectedWallet2.balance());

        Mockito.when(walletRepository.findAll()).thenReturn(Arrays.asList(entity1,entity2));
        Mockito.when(transactionRepository.findAllByWalletId(2)).thenReturn(Arrays.asList(transactionEntity));
        List<Wallet> expected = Arrays.asList(expectedWallet1,expectedWallet2);
        List<Wallet> actual = walletService.getAllWallet();
        Assertions.assertEquals(expected,actual);

    }
    @Test
    public void shouldReturnEmptyWalletList_WhenGetWallet(){
        Mockito.when(walletRepository.findAll()).thenReturn(Arrays.asList());
        List<Wallet> actual = walletService.getAllWallet();
        Assertions.assertEquals(Arrays.asList(),actual);
    }
}
