package com.gdgoc.arcive.domain.activity.dto;

import com.gdgoc.arcive.domain.activity.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "활동 정보")
public record SimpleActivityResponse(
        @Schema(description = "활동 ID", example = "1")
        Long id,

        @Schema(description = "활동 이름", example = "GDGoC Solution Challenge")
        String name
) {
    public static SimpleActivityResponse from(Activity activity) {
        return new SimpleActivityResponse(
                activity.getId(),
                activity.getName()
        );
    }
}