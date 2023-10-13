package com.example.demo.service.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.example.demo.config.Constant;
import com.example.demo.config.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Data;

@Data
public class ResponseDTO {
	private String code;
	private Object message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String detail;
	private Object response;
	private ErrorCode.Params[] params;
	
	
	public void setSuccess() {
        this.code = Constant.SUCCESS_CODE;
        this.message = Constant.SUCCESS_MSG;
    }

    public void setSuccess(Object response) {
        this.code = Constant.SUCCESS_CODE;
        this.message = Constant.SUCCESS_MSG;
        this.response = response;
    }

    public void setFail(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setFail() {
        this.code = Constant.FAIL_CODE;
        this.message = Constant.FAIL_MSG;
    }
    
    public static <T> ResponseDTO success(T data) {
        ResponseDTO res = new ResponseDTO();
        res.setCode(Constant.SUCCESS_CODE);
        res.setMessage(Constant.SUCCESS_MSG);
        res.setResponse(data);
        return res;
    }

    public static ResponseDTO success() {
        ResponseDTO res = new ResponseDTO();
        res.setCode(Constant.SUCCESS_CODE);
        res.setMessage(Constant.SUCCESS_MSG);
        return res;
    }

    public static ResponseDTO error() {
        ResponseDTO res = new ResponseDTO();
        res.setCode(Constant.FAIL_CODE);
        res.setMessage(Constant.FAIL_MSG);

        return res;
    }

    public static ResponseDTO error(String code, Object message) {
        ResponseDTO res = new ResponseDTO();
        res.setCode(code);
        res.setMessage(message);
        return res;
    }

    public static ResponseDTO error(String code, Object message, ErrorCode.Params[] args) {
        ResponseDTO res = error(code, message);
        res.setParams(args);
        return res;
    }

    public static ResponseDTO error(String code, Object message, String detail) {
        ResponseDTO res = new ResponseDTO();
        res.setCode(code);
        res.setMessage(message);
        res.setDetail(detail);
        return res;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
