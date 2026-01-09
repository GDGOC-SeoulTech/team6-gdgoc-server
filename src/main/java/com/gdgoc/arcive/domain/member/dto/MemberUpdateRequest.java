package com.gdgoc.arcive.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String bio;
    private String profileImageUrl;
    private List<String> techStacks;
    private List<String> snsLinks;
}