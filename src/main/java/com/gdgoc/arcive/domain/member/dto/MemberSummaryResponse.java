package com.gdgoc.arcive.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSummaryResponse {
    private Long id;
    private String name;
    private String profileImageUrl;
    private String role;
    private int generation;
}