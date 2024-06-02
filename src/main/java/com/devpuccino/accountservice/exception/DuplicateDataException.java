package com.devpuccino.accountservice.exception;

public class DuplicateDataException extends Exception {
    public DuplicateDataException(String message) {
        super(message);
    }
}
