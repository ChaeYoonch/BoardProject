package org.choongang.global.exceptions;

public class AlertException extends CommonException {
    public AlertException(String message, int status) {
        super(message, status);
    }
} // javascript 형태로 오류 보냄