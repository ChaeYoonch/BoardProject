package org.choongang.member.controllers;

import org.choongang.global.config.annotations.Controller;
import org.choongang.global.config.annotations.GetMapping;
import org.choongang.global.config.annotations.PostMapping;
import org.choongang.global.config.annotations.RequestMapping;

@Controller
@RequestMapping("/member") // 맨 앞의 주소는 member 로 매칭됨
public class MemberController {

    // 회원가입 양식
    @GetMapping("/join") // member/join 으로 유입됨
    public String join() {

        return "member/join"; // join.jsp 로 이동
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String joinPs(RequestJoin form) { // RequestJoin 의 값이 알아서 들어옴

        return null;
    }

    // 로그인 양식
    @GetMapping("/login") // member/login 으로 유입됨
    public String login() {

        return "member/login"; // login.jsp 로 이동
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginPs(RequestLogin form) { // RequestLogin 의 값이 알아서 들어옴

        return null;
    }
}