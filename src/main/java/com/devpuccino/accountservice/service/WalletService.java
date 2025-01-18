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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Wallet insertWallet(WalletRequest request) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletName(request.walletName());
        walletEntity.setWalletType(request.walletType());
        walletEntity.setCurrency(request.currency());
        walletEntity.setCardType(request.cardType());
        walletEntity.setCreditLimit(request.creditLimit());
        walletEntity.setPaymentDueDate(request.paymentDueDate());
        walletEntity.setBillingDate(request.billingDate());
        walletEntity = walletRepository.save(walletEntity);
        BigDecimal balance = null;
        if (walletEntity.getWalletType().equals(WalletType.CASH)) {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setTransactionType(TransactionType.INITIAL);
            transactionEntity.setTransactionDate(new Date());
            transactionEntity.setTransactionAmount(request.balance());
            transactionEntity.setWalletId(walletEntity.getWalletId());
            transactionRepository.save(transactionEntity);
            balance = transactionEntity.getTransactionAmount();
        }

        Wallet wallet = new Wallet(
                walletEntity.getWalletId().toString(),
                walletEntity.getWalletName(),
                walletEntity.getWalletType(),
                balance,
                walletEntity.getCurrency(),
                walletEntity.getCreditLimit(),
                walletEntity.getCardType(),
                walletEntity.getPaymentDueDate(),
                walletEntity.getBillingDate());
        return wallet;
    }

    public void deleteWallet(String walletId) {
        WalletEntity entity = new WalletEntity();
        entity.setWalletId(Integer.parseInt(walletId));
        walletRepository.delete(entity);
    }

    public Wallet getWalletById(String walletId) throws DataNotFoundException {
        Optional<WalletEntity> walletEntity = walletRepository.findById(Integer.parseInt(walletId));
        WalletEntity entity = walletEntity.orElseThrow(() -> new DataNotFoundException("Data not found"));
        BigDecimal balance = null;
        if (entity.getWalletType().equals(WalletType.CASH)) {
            balance = BigDecimal.valueOf(0);
            List<TransactionEntity> transactions = transactionRepository.findAllByWalletId(Integer.parseInt(walletId));
            for (TransactionEntity transaction : transactions) {
                if (transaction.getTransactionType().equals(TransactionType.INITIAL)) {
                    balance = balance.add(transaction.getTransactionAmount());
                }
            }
        }
        Wallet wallet = new Wallet(
                entity.getWalletId().toString(),
                entity.getWalletName(),
                entity.getWalletType(),
                balance,
                entity.getCurrency(),
                entity.getCreditLimit(),
                entity.getCardType(),
                entity.getPaymentDueDate(),
                entity.getBillingDate());


        return wallet;
    }

    public List<Wallet> getAllWallet() {
        List<WalletEntity> entities = walletRepository.findAll();
        return entities.stream().map((entity) -> {
            BigDecimal balance = null;
            if (entity.getWalletType().equals(WalletType.CASH)) {
                balance = BigDecimal.valueOf(0);
                List<TransactionEntity> transactions = transactionRepository.findAllByWalletId(entity.getWalletId());
                for (TransactionEntity transaction : transactions) {
                    if (transaction.getTransactionType().equals(TransactionType.INITIAL)) {
                        balance = balance.add(transaction.getTransactionAmount());
                    }
                }
            }
            Wallet wallet = new Wallet(
                    entity.getWalletId().toString(),
                    entity.getWalletName(),
                    entity.getWalletType(),
                    balance,
                    entity.getCurrency(),
                    entity.getCreditLimit(),
                    entity.getCardType(),
                    entity.getPaymentDueDate(),
                    entity.getBillingDate());
            return wallet;
        }).toList();
    }
}
