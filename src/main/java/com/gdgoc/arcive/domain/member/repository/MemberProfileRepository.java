package com.gdgoc.arcive.domain.member.repository;

import com.gdgoc.arcive.domain.member.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

}
