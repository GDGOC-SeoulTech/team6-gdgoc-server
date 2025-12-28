package com.gdgoc.arcive.domain.auth.exception;

import com.gdgoc.arcive.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-001", "잘못된 토큰입니다."),
	TOKEN_NOT_FOUNT(HttpStatus.BAD_REQUEST, "AUTH-002", "토큰이 존재하지 않습니다.")
	;

	private final HttpStatus status;
	private final String value;
	private final String message;
}
