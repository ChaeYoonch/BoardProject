package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.Controller;
import org.choongang.global.config.annotations.GetMapping;
import org.choongang.global.config.annotations.PostMapping;
import org.choongang.global.config.annotations.RequestMapping;
import org.choongang.member.services.JoinService;

@Controller
@RequestMapping("/member") // 맨 앞의 주소는 member 로 매칭됨
@RequiredArgsConstructor
public class MemberController {

    private final JoinService joinService; // JoinService 의존성으로 주입됨

    // 회원가입 양식
    @GetMapping("/join") // member/join 으로 유입됨
    public String join() {

        return "member/join"; // join.jsp 로 이동
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String joinPs(RequestJoin form) { // RequestJoin 의 값이 알아서 들어옴

        joinService.process(form);

        return "member/join";
    }

    // 로그인 양식
    @GetMapping("/login") // member/login 으로 유입됨
    public String login() {

        return "member/login"; // login.jsp 로 이동 | 임시로 탬플릿 넣어놓음
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginPs(RequestLogin form) { // RequestLogin 의 값이 알아서 들어옴

        return null;
    }
}