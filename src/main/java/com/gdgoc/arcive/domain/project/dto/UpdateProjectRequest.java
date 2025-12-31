package com.gdgoc.arcive.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "프로젝트 수정 요청")
public record UpdateProjectRequest(
        @Schema(description = "프로젝트명", example = "GDGoC 웹사이트")
        String projectName,

        @Schema(description = "기수", example = "1")
        @Positive(message = "기수는 양수여야 합니다.")
        Integer generation,

        @Schema(description = "프로젝트 설명", example = "GDGoC 공식 웹사이트 개발 프로젝트입니다.")
        String description,

        @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
        String imageUrl,

        @Schema(description = "활동 ID", example = "1")
        Long activityId
) {
}

