package com.gdgoc.arcive.infra.redis.exception;

import com.gdgoc.arcive.global.exception.BaseException;

public class RedisException extends BaseException {

    public RedisException(RedisErrorCode errorCode) { super(errorCode);}
}
