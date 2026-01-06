package com.gdgoc.arcive.domain.session.controller;

import com.gdgoc.arcive.domain.session.dto.SessionResponse;
import com.gdgoc.arcive.domain.session.service.SessionService;
import com.gdgoc.arcive.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "세션", description = "세션 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Operation(summary = "세션 소개 리스트 조회 [임채영]")
    @GetMapping("/intro")
    public ResponseEntity<ApiResponse<List<SessionResponse>>> getAllSessions(){
        return ResponseEntity.ok(ApiResponse.success(sessionService.getAllSessions()));
    }
}