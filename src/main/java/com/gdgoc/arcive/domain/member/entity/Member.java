package com.gdgoc.arcive.domain.member.entity;

import com.gdgoc.arcive.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false, length = 100)
    private String socialId;

    @Column(nullable = false)
    private String email;

    @Builder
    public Member(Role role, SocialType socialType, String socialId, String email) {
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.email = email;
    }

    // Role 변경 메서드
    public void updateRole(Role role) {
        this.role = role;
    }

    // 승인 메서드 (PENDING -> MEMBER)
    public void approve() {
        if (this.role != Role.PENDING) {
            throw new IllegalArgumentException("승인 대기 상태가 아닌 회원입니다.");
        }
        this.role = Role.MEMBER;
    }
}
