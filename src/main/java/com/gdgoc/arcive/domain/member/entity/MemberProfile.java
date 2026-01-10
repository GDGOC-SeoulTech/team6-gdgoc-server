package com.gdgoc.arcive.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_profile_id")
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String major;

    @Column(nullable = false)
    private int generation;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateOnboardingInfo(String name, String studentId, String major, int generation) {
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.generation = generation;
    }

    public void updateProfile(String bio, String profileImageUrl) {
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberProfile create(Member member, String name, String studentId, String major, int generation, String profileImageUrl) {
        MemberProfile profile = new MemberProfile();
        profile.member = member;
        profile.name = name;
        profile.studentId = studentId;
        profile.major = major;
        profile.generation = generation;
        profile.profileImageUrl = profileImageUrl;
        profile.bio = null;
        return profile;
    }
}