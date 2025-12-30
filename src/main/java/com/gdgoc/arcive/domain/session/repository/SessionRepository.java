package com.gdgoc.arcive.domain.session.repository;

import com.gdgoc.arcive.domain.session.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

}
