package com.gdgoc.arcive.domain.project.exception;

import com.gdgoc.arcive.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProjectErrorCode implements BaseErrorCode {

    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT-001", "존재하지 않는 프로젝트입니다.");

    private final HttpStatus status;
    private final String value;
    private final String message;
}



