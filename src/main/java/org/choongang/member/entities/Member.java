package org.choongang.member.entities;

import org.choongang.member.constants.UserType;

import java.time.LocalDateTime;

public class Member {
    private long userNo;
    private String email;
    private String password;
    private String userName;
    private UserType userType = UserType.USER;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
