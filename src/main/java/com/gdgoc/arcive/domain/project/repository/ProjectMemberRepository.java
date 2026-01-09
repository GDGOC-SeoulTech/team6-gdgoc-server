package com.gdgoc.arcive.domain.project.repository;

import com.gdgoc.arcive.domain.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}