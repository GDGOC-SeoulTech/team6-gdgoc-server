package com.gdgoc.arcive.domain.member.exception;

import com.gdgoc.arcive.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "존재하지 않는 회원입니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-002", "존재하지 않는 프로필입니다."),
    INVALID_MAJOR(HttpStatus.BAD_REQUEST, "MEMBER-003", "유효하지 않은 전공입니다.");

    private final HttpStatus status;
    private final String value;
    private final String message;
}