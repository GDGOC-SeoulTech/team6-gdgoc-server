package com.gdgoc.arcive.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MemberOnboardingRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "학번은 필수입니다.")
        String studentId,

        @NotBlank(message = "전공은 필수입니다.")
        String major,

        @NotNull(message = "기수는 필수입니다.")
        @Positive(message = "기수는 양수여야 합니다.")
        Integer generation,

        @NotNull(message = "파트는 필수입니다.")
        String part
) {
}