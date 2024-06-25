package org.choongang.global.exceptions;

import jakarta.servlet.http.HttpServletResponse;

public class CommonException extends RuntimeException {
    private int status;

    public CommonException(String message) {
        this(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } // 응답 코드 없이 보내면 500 오류 발생

    public CommonException(String message, int status) {
        super(message); // RuntimeException 에서 처리
        this.status = status; // 상태 코드 설정할 수 있도록 넣어 줌
    }

    public int getStatus() {
        return status; // 상태 코드 조회할 수 있도록 넣어줌
    }
}