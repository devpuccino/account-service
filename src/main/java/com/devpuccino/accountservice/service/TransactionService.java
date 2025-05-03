package com.devpuccino.accountservice.service;

import com.devpuccino.accountservice.domain.request.TransactionRequest;
import com.devpuccino.accountservice.entity.TransactionEntity;
import com.devpuccino.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Long insertTransaction(TransactionRequest request) {
        TransactionEntity result = transactionRepository.save(request.toTransactionEntity());
        return result.getTransactionId();

    }
}
