package org.choongang.global.exceptions;

public class AlertBackException extends AlertException {
    private String target;

    public AlertBackException(String message, int status, String target) {
        super(message, status);
        this.target = target;
    }

    public AlertBackException(String message, int status) {
        this(message, status, "self");
    } // 얘가 던져지면, 이전으로 돌아감

    public String getTarget() {
        return target;
    }
} // 메세지 보내고 이전 페이지로 감