package com.gdgoc.arcive.global.security.oauth2.service;

import com.gdgoc.arcive.global.security.oauth2.dto.OAuthAttributes;
import com.gdgoc.arcive.global.security.oauth2.entity.CustomOAuth2User;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.SocialType;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);  // OAuth2 정보 조회

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = SocialType.getSocialType(registrationId);

        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> originAttributes = oAuth2User.getAttributes();
        OAuthAttributes attributes = OAuthAttributes.of(socialType, nameAttributeKey, originAttributes);

        Member member;
        String socialId = attributes.getOAuth2UserInfo().getProviderId();
        if (memberRepository.existsBySocialId(socialId)) {
            member = memberRepository.findBySocialId(socialId).get();
            return new CustomOAuth2User(member.getEmail(), member.getId(), member.getRole(), false);
        } else {
            member = memberRepository.save(attributes.toEntity(socialType));
            return new CustomOAuth2User(member.getEmail(), member.getId(), member.getRole(), true);
        }
    }
}