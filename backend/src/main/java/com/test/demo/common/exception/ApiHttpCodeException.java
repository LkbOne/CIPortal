package com.test.demo.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApiHttpCodeException extends RuntimeException{
    private String code;
    private String msg;
    public ApiHttpCodeException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
