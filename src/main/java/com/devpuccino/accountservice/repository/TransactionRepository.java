package com.devpuccino.accountservice.repository;

import com.devpuccino.accountservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
    public List<TransactionEntity> findAllByWalletId(int walletId);
}
