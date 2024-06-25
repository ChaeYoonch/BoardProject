package org.choongang.global.exceptions;

public class AlertRedirectException extends AlertException {

    private String redirectUrl;
    private String target;

    public AlertRedirectException(String message, String redirectUrl, int status, String target) {
        super(message, status);
        this.redirectUrl = redirectUrl;
        this.target = target;
    }

    public AlertRedirectException(String message, String redirectUrl, int status) {
        this(message, redirectUrl, status, "self"); // 현재 창에서 이동할 수 있도록 (다르 창이 생성되지 않도록 설정)
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getTarget() {
        return target; // 미입력 시 self 로 고정됨
    }
}