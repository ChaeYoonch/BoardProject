package org.choongang.member.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.Controller;
import org.choongang.global.config.annotations.GetMapping;
import org.choongang.global.config.annotations.PostMapping;
import org.choongang.global.config.annotations.RequestMapping;
import org.choongang.member.services.JoinService;
import org.choongang.member.services.LoginService;

@Controller
@RequestMapping("/member") // 맨 앞의 주소는 member 로 매칭됨
@RequiredArgsConstructor
public class MemberController {

    private final JoinService joinService; // JoinService 의존성으로 주입됨
    private final LoginService loginService; // LoginService 의존성으로 주입됨

    // 회원가입 양식
    @GetMapping("/join") // member/join 으로 유입됨
    public String join() {

        return "member/join"; // join.jsp 로 이동
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String joinPs(RequestJoin form, HttpServletRequest request) { // RequestJoin 의 값이 알아서 들어옴

        joinService.process(form);

        String url = request.getContextPath() + "/member/login";
        String script = String.format("parent.location.replace('%s');", url);
        request.setAttribute("script", script);
        return "commons/execute_script";
    }

    // 로그인 양식
    @GetMapping("/login") // member/login 으로 유입됨
    public String login() {

        return "member/login"; // login.jsp 로 이동 | 임시로 탬플릿 넣어놓음
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginPs(RequestLogin form, HttpServletRequest request) { // RequestLogin 의 값이 알아서 들어옴

        loginService.process(form); // public void process(RequestLogin form) { } 로 연동됨

        String redirectUrl = form.getRedirectUrl();
        redirectUrl = redirectUrl == null || redirectUrl.isBlank() ? "/" : redirectUrl; // null 이거나 빈 값이면 메인 페이지

        String script = String.format("parent.location.replace('%s');", request.getContextPath() + redirectUrl); // 페이지 이동

        request.setAttribute("script", script);

        return "commons/execute_script"; // script 태그 만나면 execute_script 에 정의된대로 script 실행됨
    }

    // 로그아웃 처리
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 비우기 : 로그아웃

        return "redirect:/member/login"; // 페이지 이동 response.sendRedirect(...)
    }
}