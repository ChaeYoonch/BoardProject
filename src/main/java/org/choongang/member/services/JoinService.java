package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.Service;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.mappers.MemberMapper;
import org.choongang.member.validators.JoinValidator;

@Service
@RequiredArgsConstructor
public class JoinService {
    public final JoinValidator validator;
    private final MemberMapper mapper; // 의존성 해결 -> 알아서 주입됨!

    public void process(RequestJoin form) {
        validator.check(form); // 회원 데이터 검증됨!
    }
}