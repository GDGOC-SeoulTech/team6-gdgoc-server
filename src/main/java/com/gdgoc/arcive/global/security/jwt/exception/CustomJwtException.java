package com.gdgoc.arcive.global.security.jwt.exception;

import com.gdgoc.arcive.global.exception.BaseException;
import lombok.Getter;

@Getter
public class CustomJwtException extends BaseException {

	public CustomJwtException(JwtErrorCode errorCode) {
		super(errorCode);
	}
}
