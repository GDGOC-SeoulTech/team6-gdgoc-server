package com.gdgoc.arcive.domain.member.entity;

import com.gdgoc.arcive.domain.part.entity.Part;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private Major major;

    @Column(nullable = false)
    private int generation;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    // Part 변경 메서드
    public void updatePart(Part part) {
        this.part = part;
    }

    public void updateOnboardingInfo(String name, String studentId, Major major, int generation) {
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.generation = generation;
    }

    public void updateProfile(String bio, String profileImageUrl) {
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberProfile create(Member member, String name, String studentId, Major major, Part part, int generation, String profileImageUrl) {
        MemberProfile profile = new MemberProfile();
        profile.member = member;
        profile.name = name;
        profile.studentId = studentId;
        profile.major = major;
        profile.part = part;
        profile.generation = generation;
        profile.profileImageUrl = profileImageUrl;
        profile.bio = null;
        return profile;
    }
}