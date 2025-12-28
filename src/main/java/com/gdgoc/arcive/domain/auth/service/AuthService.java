package com.gdgoc.arcive.domain.auth.service;

import com.gdgoc.arcive.domain.auth.exception.AuthErrorCode;
import com.gdgoc.arcive.domain.auth.exception.CustomAuthException;
import com.gdgoc.arcive.global.security.dto.TempTokenInfo;
import com.gdgoc.arcive.global.security.dto.TokenResponse;
import com.gdgoc.arcive.global.security.jwt.JwtTokenProvider;
import com.gdgoc.arcive.infra.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final RedisUtil redisUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse issueAccessToken(String tempToken) {
        if (tempToken == null) {
            throw new CustomAuthException(AuthErrorCode.TOKEN_NOT_FOUNT);
        }

        TempTokenInfo value = redisUtil.getAndDeleteValue(tempToken, TempTokenInfo.class).orElseThrow(
                () -> new CustomAuthException(AuthErrorCode.INVALID_TOKEN)
        );

        return jwtTokenProvider.issueAccessToken(value.email(), value.id(), value.role());
    }

    @Transactional
    public void logout(String accessToken) {
        if (accessToken == null) {
            throw new CustomAuthException(AuthErrorCode.TOKEN_NOT_FOUNT);
        }

        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        if (expiration > 0) {
            redisUtil.saveValue("blacklist:" + accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        }
    }
}