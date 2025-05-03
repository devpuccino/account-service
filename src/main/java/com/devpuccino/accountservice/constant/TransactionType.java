package com.devpuccino.accountservice.constant;

public enum TransactionType {
    INITIAL("initial"), INCOME("income"), EXPENSE("expense");
    String value;
    TransactionType(String value) {
        this.value = value;
    }
}
