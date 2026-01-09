package com.gdgoc.arcive.domain.member.controller;

import com.gdgoc.arcive.domain.member.dto.MemberOnboardingRequest;
import com.gdgoc.arcive.domain.member.dto.MemberSummaryResponse;
import com.gdgoc.arcive.domain.member.dto.MemberDetailResponse;
import com.gdgoc.arcive.domain.member.dto.MemberUpdateRequest;
import com.gdgoc.arcive.domain.member.service.MemberService;
import com.gdgoc.arcive.global.security.oauth2.entity.CustomOAuth2User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "멤버", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/me/onboarding")
    public ResponseEntity<Void> onboardMember(
            @AuthenticationPrincipal CustomOAuth2User user,
            @RequestBody MemberOnboardingRequest request) {
        memberService.onboardMember(user.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDetailResponse> getMyProfile(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(memberService.getMemberProfile(user.getId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberDetailResponse> getMemberProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.getMemberProfile(userId));
    }


    @PatchMapping("/me")
    public ResponseEntity<Void> updateMemberProfile(
            @AuthenticationPrincipal CustomOAuth2User user,
            @RequestBody MemberUpdateRequest request) {
        memberService.updateMemberProfile(user.getId(), request);
        return ResponseEntity.ok().build();
    }


    @GetMapping("")
    public ResponseEntity<List<MemberSummaryResponse>> getMemberList(
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) String part) {
        return ResponseEntity.ok(memberService.getMemberList(generation, part));
    }


    @GetMapping("/search")
    public ResponseEntity<List<MemberSummaryResponse>> searchMembers(@RequestParam String name) {
        return ResponseEntity.ok(memberService.searchMembersByName(name));
    }


    @GetMapping("/me/projects")
    public ResponseEntity<List<String>> getMyProjects(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(memberService.getMemberProjects(user.getId()));
    }


    @GetMapping("/me/part-members")
    public ResponseEntity<List<MemberSummaryResponse>> getMyPartMembers(@AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(memberService.getMyPartMembers(user.getId()));
    }
}