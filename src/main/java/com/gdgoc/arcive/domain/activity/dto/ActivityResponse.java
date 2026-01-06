package com.gdgoc.arcive.domain.activity.dto;

import com.gdgoc.arcive.domain.activity.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "활동 정보")
public record ActivityResponse(
        @Schema(description = "활동 ID", example = "1")
        Long id,

        @Schema(description = "활동 이름", example = "GDGoC Solution Challenge")
        String name,

        @Schema(description = "활동 설명", example = "GDGoc의 팀 프로젝트 대회입니다.")
        String description,

        @Schema(description = "활동 이미지 URL", example = "https://example.com/image.jpg")
        String imageUrl
) {
    public static ActivityResponse from(Activity activity) {
        return new ActivityResponse(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getImageUrl()
        );
    }
}