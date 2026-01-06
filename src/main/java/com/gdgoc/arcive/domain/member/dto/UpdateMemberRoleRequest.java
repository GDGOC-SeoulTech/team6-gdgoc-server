package com.gdgoc.arcive.domain.member.dto;

import com.gdgoc.arcive.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원 권한 수정 요청")
public record UpdateMemberRoleRequest(
        @Schema(description = "변경할 권한", example = "CORE", required = true)
        @NotNull(message = "권한은 필수입니다.")
        Role role
) {
}



