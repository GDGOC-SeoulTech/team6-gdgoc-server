package com.gdgoc.arcive.global.security.oauth2.handler;

import com.gdgoc.arcive.domain.member.entity.Role;
import com.gdgoc.arcive.global.security.dto.TempTokenInfo;
import com.gdgoc.arcive.global.security.oauth2.entity.CustomOAuth2User;
import com.gdgoc.arcive.infra.redis.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.front-redirect-url}")
    private String redirectUrl;

    private final RedisUtil redisUtil;

    private final Long tempTokenExpirationTime = 1200L;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();

        String tempToken = issueTempToken(principal.getEmail(), principal.getId(), principal.getRole());

        ResponseCookie cookie = ResponseCookie.from("tempToken", tempToken)
                .maxAge(tempTokenExpirationTime)
                .path("/")
                .sameSite("None")
                .secure(true) // SameSite=None일 때는 Secure 필수
                .build();

//        String fullRedirectUrl = UriComponentsBuilder
//                .fromUriString(redirectUrl)
//                .queryParam("token", tokenInfo.token())
//                .build()
//                .toUriString();

        response.addHeader("Set-Cookie", cookie.toString());
        response.sendRedirect(redirectUrl);
    }

    private String issueTempToken(String email, Long id, Role role) {
        String tempToken = UUID.randomUUID().toString();
        redisUtil.saveValue(tempToken, new TempTokenInfo(email, id, role), tempTokenExpirationTime, TimeUnit.SECONDS);
        return tempToken;
    }
}