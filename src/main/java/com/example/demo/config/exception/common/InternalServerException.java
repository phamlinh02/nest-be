package com.example.demo.config.exception.common;

import com.example.demo.config.exception.ErrorCode;
import com.example.demo.config.exception.ServerException;
import org.springframework.http.HttpStatus;

public class InternalServerException extends ServerException {
    public InternalServerException(ErrorCode errorCode, ErrorCode.Params... params) {
        super(errorCode, HttpStatus.INTERNAL_SERVER_ERROR, params);
    }
}
