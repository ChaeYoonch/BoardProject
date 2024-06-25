package org.choongang.member.validators;

import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.Component;
import org.choongang.global.validators.EmailValidator;
import org.choongang.global.validators.RequiredValidator;
import org.choongang.global.validators.Validator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.mappers.MemberMapper;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator<RequestJoin>, RequiredValidator, EmailValidator { // 인터페이스 형태로 추가

    private final MemberMapper mapper;

    /* private final MemberMapper mapper; // final (= 상수) 쓰면 아래꺼 무조건 써야 함

    public JoinValidator(MemberMapper mapper) {
        this.mapper = mapper;
    } // 원래 이렇게 만들어야 함 -> 너무 길음 */

    @Override
    public void check(RequestJoin form) {

    }
}