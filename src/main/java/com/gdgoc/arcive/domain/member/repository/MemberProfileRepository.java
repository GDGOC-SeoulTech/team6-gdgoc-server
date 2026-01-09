package com.gdgoc.arcive.domain.member.repository;

import com.gdgoc.arcive.domain.member.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    @Query("SELECT mp FROM MemberProfile mp JOIN FETCH mp.member m WHERE m.id = :memberId")
    Optional<MemberProfile> findByMemberIdWithMember(@Param("memberId") Long memberId);

    @Query("SELECT mp FROM MemberProfile mp JOIN FETCH mp.member m")
    List<MemberProfile> findAllWithMember();

    @Query("SELECT mp FROM MemberProfile mp JOIN FETCH mp.member m " +
            "WHERE (:generation IS NULL OR mp.generation = :generation) " +
            "AND (:part IS NULL OR m.role = :part)")
    List<MemberProfile> findByGenerationAndPart(@Param("generation") Integer generation,
                                                @Param("part") String part);
}