package org.choongang.global.advices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.ControllerAdvice;
import org.choongang.global.config.annotations.ModelAttribute;
import org.choongang.global.exceptions.*;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;

@RequiredArgsConstructor
@ControllerAdvice("org.choongang")
public class CommonControllerAdvice {

    private final MemberUtil memberUtil;

    @ModelAttribute
    public boolean isLogin() {
        return memberUtil.isLogin();
    } // 메서드명 isLogin 자체가 모두 공유하게 함

    @ModelAttribute
    public boolean isAdmin() {
        return memberUtil.isAdmin();
    } // 공통으로 유지할 값

    @ModelAttribute
    public Member loggedMember () {
        return memberUtil.getMember();
    }

    /**
     * 공통 에러 페이지 처리
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {

        e.printStackTrace();

        if (e instanceof CommonException commonException) {
            int status = commonException.getStatus(); // 응답 코드 가져옴
            response.setStatus(status); // 응답코드 response 형태로 넣음

            StringBuffer sb = new StringBuffer(1000);
            if (e instanceof AlertException) {
                sb.append(String.format("alert('%s');", e.getMessage()));
            }

            if (e instanceof AlertBackException alertBackException) {
                String target = alertBackException.getTarget();
                sb.append(String.format("%s.history.back();", target));
            }

            if (e instanceof AlertRedirectException alertRedirectException) {
                String target = alertRedirectException.getTarget();
                String url = alertRedirectException.getRedirectUrl();

                sb.append(String.format("%s.location.replace('%s')", target, url));
            }

            if (!sb.isEmpty()) { // sb 가 있을 때
                request.setAttribute("script", sb.toString());
                return "commons/execute_script";
            }
        } else {
            // CommonException 으로 정의한 예외가 아닌 경우 -> 응답 코드 500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "errors/error";
    }
}