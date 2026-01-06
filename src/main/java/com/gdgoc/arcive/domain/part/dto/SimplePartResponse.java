package com.gdgoc.arcive.domain.part.dto;

import com.gdgoc.arcive.domain.part.entity.Part;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파트 정보")
public record SimplePartResponse(
        @Schema(description = "파트 ID", example = "1")
        Long id,

        @Schema(description = "파트 이름", example = "FE")
        String partName
) {
    public static SimplePartResponse from(Part part) {
        return new SimplePartResponse(
                part.getId(),
                part.getPartName()
        );
    }
}