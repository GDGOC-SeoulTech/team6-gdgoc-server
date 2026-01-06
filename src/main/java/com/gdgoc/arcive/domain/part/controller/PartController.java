package com.gdgoc.arcive.domain.part.controller;

import com.gdgoc.arcive.domain.part.dto.PartResponse;
import com.gdgoc.arcive.domain.part.dto.SimplePartResponse;
import com.gdgoc.arcive.domain.part.service.PartService;
import com.gdgoc.arcive.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "파트", description = "파트 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parts")
public class PartController {

    private final PartService partService;

    @Operation(summary = "파트 리스트 조회 [임채영]")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SimplePartResponse>>> getAllSimpleParts() {
        return ResponseEntity.ok(ApiResponse.success(partService.getAllSimpleParts()));
    }

    @Operation(summary = "파트 소개 리스트 조회 [임채영]")
    @GetMapping("/intro")
    public ResponseEntity<ApiResponse<List<PartResponse>>> getAllParts() {
        return ResponseEntity.ok(ApiResponse.success(partService.getAllParts()));
    }
}