package com.gdgoc.arcive.domain.activity.controller;

import com.gdgoc.arcive.domain.activity.dto.ActivityResponse;
import com.gdgoc.arcive.domain.activity.dto.SimpleActivityResponse;
import com.gdgoc.arcive.domain.activity.service.ActivityService;
import com.gdgoc.arcive.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "활동", description = "활동 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/activities/intro")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "활동 리스트 조회 [임채영]")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SimpleActivityResponse>>> getAllActivities() {
        return ResponseEntity.ok(ApiResponse.success(activityService.getAllSimpleActivities()));
    }

    @Operation(summary = "활동 소개 리스트 조회 [임채영]")
    @GetMapping("/intro")
    public ResponseEntity<ApiResponse<List<ActivityResponse>>> getAllActivitiesIntro() {
        return ResponseEntity.ok(ApiResponse.success(activityService.getAllActivities()));
    }
}