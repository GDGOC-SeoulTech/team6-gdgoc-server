package com.gdgoc.arcive.infra.s3.exception;

import com.gdgoc.arcive.global.exception.BaseException;

public class S3Exception extends BaseException {

    public S3Exception(S3ErrorCode errorCode) { super(errorCode);}
}
