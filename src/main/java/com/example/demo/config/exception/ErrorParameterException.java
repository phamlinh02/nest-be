package com.example.demo.config.exception;


import com.example.demo.config.exception.common.ClientCauseException;

public class ErrorParameterException extends ClientCauseException {
    public ErrorParameterException() {
        super(ErrorCode.ERROR_PARAMETER);
    }
}
