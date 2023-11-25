package com.example.demo.config.exception.common;

public class NotComparePriceException extends RuntimeException {
    public NotComparePriceException(String message) {
        super(message);
    }

    public NotComparePriceException() {
        super();
    }
}
