package com.gdgoc.arcive.domain.notification.dto;

import com.gdgoc.arcive.domain.notification.entity.DiscordNotificationLog;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Discord 알림 로그 정보")
public record DiscordNotificationLogResponse(
        @Schema(description = "로그 ID", example = "1")
        Long id,

        @Schema(description = "이벤트 타입", example = "MEMBER_APPROVED")
        String eventType,

        @Schema(description = "성공 여부", example = "true")
        boolean isSuccess,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "회원 ID", example = "1")
        Long memberId,

        @Schema(description = "회원 이메일", example = "user@example.com")
        String memberEmail
) {
    public static DiscordNotificationLogResponse from(DiscordNotificationLog log) {
        return new DiscordNotificationLogResponse(
                log.getId(),
                log.getEventType(),
                log.isSuccess(),
                log.getCreatedAt(),
                log.getMember().getId(),
                log.getMember().getEmail()
        );
    }
}



