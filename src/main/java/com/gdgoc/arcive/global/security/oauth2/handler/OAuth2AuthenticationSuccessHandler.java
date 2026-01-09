package com.gdgoc.arcive.global.security.oauth2.handler;

import com.gdgoc.arcive.domain.member.entity.Role;
import com.gdgoc.arcive.global.security.dto.TempTokenInfo;
import com.gdgoc.arcive.global.security.oauth2.entity.CustomOAuth2User;
import com.gdgoc.arcive.infra.redis.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

    @Value("${spring.security.oauth2.client.existing-user-redirect-url}")
    private String existingUserRedirectUrl;

    @Value("${spring.security.oauth2.client.new-user-redirect-url}")
    private String newUserRedirectUrl;

    private final RedisService redisService;
    private final Environment environment;

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

        if (!principal.isNew()) {
            response.sendRedirect(existingUserRedirectUrl);
        } else {
            response.sendRedirect(newUserRedirectUrl);
        }
    }

    private String issueTempToken(String email, Long id, Role role) {
        String tempToken = UUID.randomUUID().toString();
        // 로컬 개발 환경에서만 Redis 연결 실패 처리
        if (isLocalProfile()) {
            try {
                redisService.saveValue(tempToken, new TempTokenInfo(email, id, role), tempTokenExpirationTime, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.warn("Redis 연결 실패로 tempToken 저장 실패. Redis가 실행 중인지 확인하세요. 에러: {}", e.getMessage());
                // Redis 없이도 로그인은 진행되도록 함 (개발 환경용)
            }
        } else {
            // 배포 환경에서는 Redis 저장 실패 시 예외 발생
            redisService.saveValue(tempToken, new TempTokenInfo(email, id, role), tempTokenExpirationTime, TimeUnit.SECONDS);
        }
        return tempToken;
    }

    private boolean isLocalProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("local".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
