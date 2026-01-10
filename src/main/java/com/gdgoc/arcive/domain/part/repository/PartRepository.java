package com.gdgoc.arcive.domain.part.repository;

import com.gdgoc.arcive.domain.part.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findByPartName(String partName);
}
