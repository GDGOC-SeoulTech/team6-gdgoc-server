package com.gdgoc.arcive.global.security.oauth2.dto;

import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.Role;
import com.gdgoc.arcive.domain.member.entity.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * social별 분기 처리
 */
@Getter
public class OAuthAttributes {

    private final String nameAttributeKey;
    private final OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType, String nameAttributeKey, Map<String, Object> attributes) {
        if (socialType == SocialType.GOOGLE) {
            return ofGoogle(nameAttributeKey, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .oAuth2UserInfo(new GoogleUserInfo(attributes))
                .build();
    }

    public Member toEntity(SocialType socialType) {
        return Member.builder()
                .role(Role.PENDING)
                .socialId(oAuth2UserInfo.getProviderId())
                .socialType(socialType)
                .email(oAuth2UserInfo.getEmail())
                .build();
    }
}
