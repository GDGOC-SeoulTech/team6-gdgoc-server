package com.gdgoc.arcive.domain.activity.repository;

import com.gdgoc.arcive.domain.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}

