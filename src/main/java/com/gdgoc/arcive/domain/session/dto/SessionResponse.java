package com.gdgoc.arcive.domain.session.dto;

import com.gdgoc.arcive.domain.session.entity.Session;
import com.gdgoc.arcive.infra.s3.util.S3Util;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "세션 정보")
public record SessionResponse(
        @Schema(description = "세션 ID", example = "1")
        Long id,
        @Schema(description = "주차", example = "1")
        int week,
        @Schema(description = "세션 제목", example = "React 기초")
        String title,
        @Schema(description = "세션 내용", example = "React의 기본 개념과 컴포넌트에 대해 학습했습니다.")
        String content,
        @Schema(description = "파트명", example = "FE")
        String partName,
        @Schema(description = "이미지 URL", example = "https://example.com/image.png")
        String imageUrl
) {
    public static SessionResponse from(Session session, String urlPrefix) {
        return new SessionResponse(
                session.getId(),
                session.getWeek(),
                session.getTitle(),
                session.getContent(),
                session.getPart().getPartName(),
                S3Util.buildImageUrl(urlPrefix, session.getImageUrl())
        );
    }
}