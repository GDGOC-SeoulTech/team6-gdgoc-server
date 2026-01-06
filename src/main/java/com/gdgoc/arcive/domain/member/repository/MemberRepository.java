package com.gdgoc.arcive.domain.member.repository;

import com.gdgoc.arcive.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);
    boolean existsBySocialId(String socialId);
}

