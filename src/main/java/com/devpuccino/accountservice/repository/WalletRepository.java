package com.devpuccino.accountservice.repository;

import com.devpuccino.accountservice.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity,Integer> {
}
