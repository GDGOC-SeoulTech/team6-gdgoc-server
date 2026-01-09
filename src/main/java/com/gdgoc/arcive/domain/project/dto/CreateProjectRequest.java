package com.gdgoc.arcive.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "프로젝트 등록 요청")
public record CreateProjectRequest(
        @Schema(description = "프로젝트명", example = "GDGoC 웹사이트", required = true)
        @NotBlank(message = "프로젝트명은 필수입니다.")
        String projectName,

        @Schema(description = "기수", example = "1", required = true)
        @NotNull(message = "기수는 필수입니다.")
        @Positive(message = "기수는 양수여야 합니다.")
        Integer generation,

        @Schema(description = "프로젝트 설명", example = "GDGoC 공식 웹사이트 개발 프로젝트입니다.", required = true)
        @NotBlank(message = "프로젝트 설명은 필수입니다.")
        String description,

        @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
        String imageUrl,

        @Schema(description = "활동 ID", example = "1", required = true)
        @NotNull(message = "활동 ID는 필수입니다.")
        Long activityId
) {
}





