package com.gdgoc.arcive.domain.part.repository;

import com.gdgoc.arcive.domain.part.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}
