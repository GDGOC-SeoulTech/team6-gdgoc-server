package com.gdgoc.arcive.domain.project.exception;

import com.gdgoc.arcive.global.exception.BaseException;

public class ProjectException extends BaseException {

    public ProjectException(ProjectErrorCode errorCode) {
        super(errorCode);
    }
}





