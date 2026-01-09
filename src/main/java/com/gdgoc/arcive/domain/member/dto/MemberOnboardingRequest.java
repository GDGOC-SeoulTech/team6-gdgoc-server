package com.gdgoc.arcive.domain.member.dto;

import com.gdgoc.arcive.domain.member.entity.Major;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberOnboardingRequest {
    private String name;
    private String studentId;
    private Major major;
    private Integer generation;
}