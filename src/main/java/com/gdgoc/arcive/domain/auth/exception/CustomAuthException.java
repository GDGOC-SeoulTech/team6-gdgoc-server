package com.gdgoc.arcive.domain.auth.exception;

import com.gdgoc.arcive.global.exception.BaseException;
import lombok.Getter;

@Getter
public class CustomAuthException extends BaseException {

	public CustomAuthException(AuthErrorCode errorCode) {
		super(errorCode);
	}
}
