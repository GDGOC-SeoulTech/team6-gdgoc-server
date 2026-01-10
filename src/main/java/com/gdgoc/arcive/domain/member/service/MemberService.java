package com.gdgoc.arcive.domain.member.service;

import com.gdgoc.arcive.domain.member.dto.*;
import com.gdgoc.arcive.domain.member.entity.Major;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.MemberProfile;
import com.gdgoc.arcive.domain.member.exception.MemberErrorCode;
import com.gdgoc.arcive.domain.member.exception.MemberException;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import com.gdgoc.arcive.domain.member.repository.MemberProfileRepository;
import com.gdgoc.arcive.domain.part.entity.Part;
import com.gdgoc.arcive.domain.part.exception.PartErrorCode;
import com.gdgoc.arcive.domain.part.exception.PartException;
import com.gdgoc.arcive.domain.part.repository.PartRepository;
import com.gdgoc.arcive.domain.project.dto.ProjectResponse;
import com.gdgoc.arcive.domain.project.repository.ProjectMemberRepository;
import com.gdgoc.arcive.infra.s3.config.S3Properties;
import com.gdgoc.arcive.infra.s3.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final PartRepository partRepository;
    private final S3Properties s3Properties;

    @Transactional
    public void onboardMember(Long memberId, MemberOnboardingRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Part part = partRepository.findByPartName(request.part())
                .orElseThrow(() -> new PartException(PartErrorCode.PART_NOT_FOUND));

        Major major = parseMajor(request.major());

        String profileImageName = getRandomProfileImageName();

        MemberProfile profile = memberProfileRepository.findByMemberIdWithMember(memberId)
                .orElseGet(() -> createNewProfile(member, request, major, part, profileImageName));

        if (profile.getId() != null) {
            profile.updateOnboardingInfo(
                    request.name(),
                    request.studentId(),
                    major,
                    request.generation()
            );
        } else {
            memberProfileRepository.save(profile);
        }
    }

    private String getRandomProfileImageName() {
        List<String> defaultProfileNames = List.of("fm_1.png", "fm_2.png", "fm_3.png", "fm_4.png", "fm_5.png",
                "m_1.png", "m_2.png", "m_3.png", "m_4.png", "m_5.png");

        return defaultProfileNames.get(ThreadLocalRandom.current().nextInt(defaultProfileNames.size()));
    }

    private Major parseMajor(String description) {
        try {
            return Major.fromDescription(description);
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.INVALID_MAJOR);
        }
    }

    private MemberProfile createNewProfile(Member member, MemberOnboardingRequest request, Major major, Part part, String profileImageName) {
        return MemberProfile.create(
                member,
                request.name(),
                request.studentId(),
                major,
                part,
                request.generation(),
                getRandomProfileImageName()
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
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        String myPart = me.getRole().name();

        return memberProfileRepository.findByGenerationAndPart(null, myPart).stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> getMemberProjects(Long userId) {
        return projectMemberRepository.findAll().stream()
                .filter(pm -> pm.getMember().getId().equals(userId))
                .map(pm -> ProjectResponse.from(pm.getProject()))
                .collect(Collectors.toList());
    }


    private MemberProfile findProfileByMemberIdOptimized(Long memberId) {
        return memberProfileRepository.findByMemberIdWithMember(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.PROFILE_NOT_FOUND));
    }


    private MemberDetailResponse convertToDetailResponse(MemberProfile profile) {
        Member member = profile.getMember();
        String imageUrl = getProfileImageUrl(profile.getProfileImageUrl());

        return MemberDetailResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .email(member.getEmail())
                .studentId(profile.getStudentId())
                .part(profile.getPart().getPartName())
                .major(profile.getMajor().name())
                .generation(profile.getGeneration())
                .bio(profile.getBio())
                .profileImageUrl(imageUrl)
                .role(member.getRole().name())
                .build();
    }

    private String getProfileImageUrl(String imageUrl) {
        return S3Util.buildImageUrlWithFilePath(
                s3Properties.getS3().getUrlPrefix(),
                s3Properties.getS3().getPaths().getDefaultProfileImage(),
                imageUrl
        );
    }

    private MemberSummaryResponse convertToSummaryResponse(MemberProfile profile) {
        Member member = profile.getMember();
        return MemberSummaryResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .profileImageUrl(getProfileImageUrl(profile.getProfileImageUrl()))
                .role(member.getRole().name())
                .part(profile.getPart().getPartName())
                .generation(profile.getGeneration())
                .build();
    }
}