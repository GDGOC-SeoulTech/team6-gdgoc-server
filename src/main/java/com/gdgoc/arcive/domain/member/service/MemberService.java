package com.gdgoc.arcive.domain.member.service;

import com.gdgoc.arcive.domain.member.dto.*;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.MemberProfile;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import com.gdgoc.arcive.domain.member.repository.MemberProfileRepository;
import com.gdgoc.arcive.domain.project.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Transactional
    public void onboardMember(Long memberId, MemberOnboardingRequest request) {

        MemberProfile profile = findProfileByMemberIdOptimized(memberId);


        profile.updateOnboardingInfo(
                request.getName(),
                request.getStudentId(),
                request.getMajor(),
                request.getGeneration()
        );
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberUpdateRequest request) {
        MemberProfile profile = findProfileByMemberIdOptimized(memberId);

        profile.updateProfile(request.getBio(), request.getProfileImageUrl());
    }

    public MemberDetailResponse getMemberProfile(Long memberId) {
        MemberProfile profile = findProfileByMemberIdOptimized(memberId);
        return convertToDetailResponse(profile);
    }

    public List<MemberSummaryResponse> getMemberList(Integer generation, String part) {

        return memberProfileRepository.findByGenerationAndPart(generation, part).stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<MemberSummaryResponse> searchMembersByName(String name) {

        return memberProfileRepository.findAllWithMember().stream()
                .filter(profile -> profile.getName().contains(name))
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<MemberSummaryResponse> getMyPartMembers(Long currentMemberId) {

        Member me = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

        String myPart = me.getRole().name();

        return memberProfileRepository.findByGenerationAndPart(null, myPart).stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<String> getMemberProjects(Long userId) {
        return projectMemberRepository.findAll().stream()
                .filter(pm -> pm.getMember().getId().equals(userId))
                .map(pm -> pm.getProject().getProjectName())
                .collect(Collectors.toList());
    }


    private MemberProfile findProfileByMemberIdOptimized(Long memberId) {
        return memberProfileRepository.findByMemberIdWithMember(memberId)
                .orElseThrow(() -> new IllegalArgumentException("PROFILE_NOT_FOUND"));
    }


    private MemberDetailResponse convertToDetailResponse(MemberProfile profile) {
        Member member = profile.getMember();
        return MemberDetailResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .email(member.getEmail())
                .studentId(profile.getStudentId())
                .major(profile.getMajor().name())
                .generation(profile.getGeneration())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .role(member.getRole().name())
                .build();
    }


    private MemberSummaryResponse convertToSummaryResponse(MemberProfile profile) {
        Member member = profile.getMember();
        return MemberSummaryResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .profileImageUrl(profile.getProfileImageUrl())
                .role(member.getRole().name())
                .generation(profile.getGeneration())
                .build();
    }
}