package com.gdgoc.arcive.domain.part.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파트 수정 요청")
public record UpdatePartRequest(
        @Schema(description = "파트 이름", example = "FE")
        String partName,

        @Schema(description = "파트 설명", example = "프론트엔드 개발 파트입니다.")
        String description
) {
}

