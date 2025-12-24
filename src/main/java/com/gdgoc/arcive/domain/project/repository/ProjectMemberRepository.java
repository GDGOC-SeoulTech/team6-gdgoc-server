package com.gdgoc.arcive.domain.project.repository;

import com.gdgoc.arcive.domain.project.entity.ProjectMember;
import com.gdgoc.arcive.domain.project.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

}

