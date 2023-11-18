package com.example.demo.config.exception.common;

public class NotEnoughException extends RuntimeException {
    public NotEnoughException(String message) {
        super(message);
    }

    public NotEnoughException() {
        super();
    }
}
