package com.gdgoc.arcive.domain.admin.controller;

import com.gdgoc.arcive.domain.admin.service.AdminService;
import com.gdgoc.arcive.domain.member.dto.MemberResponse;
import com.gdgoc.arcive.domain.member.dto.UpdateMemberRoleRequest;
import com.gdgoc.arcive.domain.project.dto.CreateProjectRequest;
import com.gdgoc.arcive.domain.project.dto.ProjectResponse;
import com.gdgoc.arcive.domain.project.dto.UpdateProjectRequest;
import com.gdgoc.arcive.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자", description = "관리자 전용 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "가입 사용자 목록 조회 (승인 대기 포함) [대훈]", description = "모든 가입 사용자 목록을 조회합니다.")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllMembers()));
    }

    @Operation(summary = "멤버 승인 [대훈]", description = "승인 대기 상태의 멤버를 승인합니다. (PENDING -> MEMBER)")
    @PatchMapping("/users/{userId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveMember(
            @Parameter(description = "회원 ID", example = "1")
            @PathVariable Long userId) {
        adminService.approveMember(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "멤버 권한 수정 [대훈]", description = "멤버의 권한을 수정합니다.")
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateMemberRole(
            @Parameter(description = "회원 ID", example = "1")
            @PathVariable Long userId,
            @Valid @RequestBody UpdateMemberRoleRequest request) {
        adminService.updateMemberRole(userId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "프로젝트 등록 [대훈]", description = "새로운 프로젝트를 등록합니다.")
    @PostMapping("/projects")
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createProject(request)));
    }

    @Operation(summary = "프로젝트 수정 [대훈]", description = "프로젝트 정보를 수정합니다.")
    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @Parameter(description = "프로젝트 ID", example = "1")
            @PathVariable Long projectId,
            @Valid @RequestBody UpdateProjectRequest request) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateProject(projectId, request)));
    }

    @Operation(summary = "프로젝트 삭제 [대훈]", description = "프로젝트를 삭제합니다.")
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @Parameter(description = "프로젝트 ID", example = "1")
            @PathVariable Long projectId) {
        adminService.deleteProject(projectId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

