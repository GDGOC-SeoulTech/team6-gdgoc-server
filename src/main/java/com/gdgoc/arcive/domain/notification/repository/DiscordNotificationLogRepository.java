package com.gdgoc.arcive.domain.notification.repository;

import com.gdgoc.arcive.domain.notification.entity.DiscordNotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscordNotificationLogRepository extends JpaRepository<DiscordNotificationLog, Long> {

    List<DiscordNotificationLog> findAllByOrderByCreatedAtDesc();
}

