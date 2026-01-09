package com.gdgoc.arcive.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String studentId;
    private String major;
    private int generation;
    private String bio;
    private String profileImageUrl;
    private String role;
}

