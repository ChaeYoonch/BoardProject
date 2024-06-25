package org.choongang.member.controllers;

import lombok.Data;

@Data
public class RequestLogin { // 로그인 데이터
    private String email;
    private String password;
    private boolean saveEmail;
}