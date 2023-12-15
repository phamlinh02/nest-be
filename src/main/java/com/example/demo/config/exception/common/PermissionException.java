package com.example.demo.config.exception.common;

import com.example.demo.config.exception.ErrorCode;
import com.example.demo.config.exception.ServerException;
import org.springframework.http.HttpStatus;

public class PermissionException extends ServerException {
    public PermissionException(ErrorCode errorCode, ErrorCode.Params... params) {
        super(errorCode, HttpStatus.UNAUTHORIZED, params);
    }
    public PermissionException(){}
}
