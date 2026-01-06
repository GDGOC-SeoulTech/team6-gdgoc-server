package com.gdgoc.arcive.global.security.jwt;

import com.gdgoc.arcive.global.security.oauth2.entity.CustomOAuth2User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenAuthenticator {

    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuthentication(Claims claims) {
        CustomOAuth2User oAuth2User = new CustomOAuth2User(
                jwtTokenProvider.getEmail(claims),
                jwtTokenProvider.getId(claims),
                jwtTokenProvider.getRole(claims),
                false
        );

        // 인증용 객체 생성
        return new UsernamePasswordAuthenticationToken(
                oAuth2User,
                null,
                oAuth2User.getAuthorities());
    }
}
