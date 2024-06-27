package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.Component;
import org.choongang.global.config.containers.BeanContainer;
import org.choongang.member.constants.UserType;
import org.choongang.member.entities.Member;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    // 로그인 여부
    public boolean isLogin() {
        return getMember() != null; // 값이 있으면 로그인 상태 O
    }

    // 관리자 여부
    public boolean isAdmin() {
        if (isLogin()) {
            Member member = getMember(); // login 상태 O

            return member.getUserType() == UserType.ADMIN; // UserType 이 ADMIN 인 경우
        }

        return false;
    }

    /**
     * 로그인한 회원 정보 | 조회가 되면 로그인 상태
     * @return
     */
    public Member getMember() {
        HttpSession session = BeanContainer.getInstance().getBean(HttpSession.class);
        Member member = (Member)session.getAttribute("member");

        return member;
    }
}