package com.gdgoc.arcive.domain.project.dto;

import com.gdgoc.arcive.domain.project.entity.Project;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "프로젝트 정보")
public record ProjectResponse(
        @Schema(description = "프로젝트 ID", example = "1")
        Long id,

        @Schema(description = "프로젝트명", example = "GDGoC 웹사이트")
        String projectName,

        @Schema(description = "기수", example = "1")
        Integer generation,

        @Schema(description = "프로젝트 설명", example = "GDGoC 공식 웹사이트 개발 프로젝트입니다.")
        String description,

        @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
        String imageUrl,

        @Schema(description = "활동 ID", example = "1")
        Long activityId,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "수정일시")
        LocalDateTime updatedAt
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getProjectName(),
                project.getGeneration(),
                project.getDescription(),
                project.getImageUrl(),
                project.getActivity().getId(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}

