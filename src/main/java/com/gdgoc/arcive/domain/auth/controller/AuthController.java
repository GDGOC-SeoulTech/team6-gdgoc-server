package com.gdgoc.arcive.domain.auth.controller;

import com.gdgoc.arcive.domain.auth.service.AuthService;
import com.gdgoc.arcive.global.response.ApiResponse;
import com.gdgoc.arcive.global.security.dto.TokenResponse;
import com.gdgoc.arcive.global.security.jwt.resolver.AccessToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "액세스 토큰 발급 [임채영]", description = "구글 로그인 후 저장된 쿠키를 자동 전송되게 설정해주시면 됩니다.")
    @PostMapping("/token")
    public ResponseEntity<ApiResponse<TokenResponse>> issueAccessToken(
            @Parameter(hidden = true)
            @CookieValue(name = "tempToken", required = false) String tempToken) {
        return ResponseEntity.ok(ApiResponse.success(authService.issueAccessToken(tempToken)));
    }

    @Operation(summary = "로그아웃 [임채영]")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Parameter(hidden = true)
            @AccessToken String accessToken) {
        authService.logout(accessToken);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}