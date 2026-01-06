package com.gdgoc.arcive.domain.member.dto;

import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "회원 정보")
public record MemberResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "user@example.com")
        String email,

        @Schema(description = "권한", example = "MEMBER")
        Role role,

        @Schema(description = "소셜 타입", example = "GOOGLE")
        String socialType,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "수정일시")
        LocalDateTime updatedAt
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getRole(),
                member.getSocialType() != null ? member.getSocialType().name() : null,
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}

