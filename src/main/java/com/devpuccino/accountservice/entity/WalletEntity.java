package com.devpuccino.accountservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@Data
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Integer walletId;
    @Column(name = "wallet_name")
    private String walletName;
    @Column(name = "currency")
    private String currency;
    @Column(name = "wallet_type")
    private String walletType;
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
    @Column(name = "payment_due_date")
    private String paymentDueDate;
    @Column(name = "billing_date")
    private String billingDate;
}
