package com.example.demo.config.exception.common;

import com.example.demo.config.exception.ErrorCode;
import com.example.demo.config.exception.ServerException;
import org.springframework.http.HttpStatus;

public class ClientCauseException extends ServerException {
    public ClientCauseException(ErrorCode errorCode, ErrorCode.Params... params) {
        super(errorCode, HttpStatus.BAD_REQUEST, params);
    }

}
