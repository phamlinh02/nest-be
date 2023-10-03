package com.example.demo.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerException extends RuntimeException {
    private ErrorCode errorCode;
    private HttpStatus status;
    private ErrorCode.Params[] args;
    private String[] customMessage;

    public ServerException(String message) {
        super(message, new Throwable());
        this.errorCode = ErrorCode.UNEXPECTED;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ServerException() {
        super(new Throwable());
    }

    public ServerException(String message, HttpStatus status) {
        this(message);
        this.status = status;
    }

    public ServerException(String message, HttpStatus status, ErrorCode.Params... args) {
        this(message);
        this.status = status;
        this.args = args;
    }

    public ServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServerException(ErrorCode errorCode, ErrorCode.Params... params) {
        this.errorCode = errorCode;
        this.args = params;
    }

    public ServerException(ErrorCode errorCode, HttpStatus status, ErrorCode.Params... params) {
        this.errorCode = errorCode;
        this.args = params;
        this.status = status;
    }

    public ServerException(ErrorCode errorCode, HttpStatus status, String... customMessage) {
        this.errorCode = errorCode;
        this.customMessage = customMessage;
        this.status = status;
    }

    public ServerException(HttpStatus status) {
        this.status = status;
    }
}
