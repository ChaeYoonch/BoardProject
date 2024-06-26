package org.choongang.admin.advices;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Interceptor;
import org.choongang.global.config.annotations.ControllerAdvice;
import org.choongang.member.MemberUtil;

@RequiredArgsConstructor
@ControllerAdvice("org.choongang.admin")
public class AdminControllerAdvice implements Interceptor {

    private final MemberUtil memberUtil;

    @Override
    public boolean preHandle() { // 관리자가 아니면 통과 X

        System.out.println("preHandle()!");

        return false;
    }
}