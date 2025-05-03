package com.devpuccino.accountservice.entity;

import com.devpuccino.accountservice.constant.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "transaction")
@Entity
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    @Column(name = "transaction_date")
    private Date transactionDate;
    @Column(name = "wallet_id")
    private Integer walletId;
    @Column(name = "category_id")
    private Integer categoryId;
}
