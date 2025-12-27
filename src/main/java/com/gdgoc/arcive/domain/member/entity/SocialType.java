package com.gdgoc.arcive.domain.member.entity;

import lombok.Getter;

@Getter
public enum SocialType {
    GOOGLE;

    public static SocialType getSocialType(String registrationId) {
        return SocialType.valueOf(registrationId.toUpperCase());
    }
}

