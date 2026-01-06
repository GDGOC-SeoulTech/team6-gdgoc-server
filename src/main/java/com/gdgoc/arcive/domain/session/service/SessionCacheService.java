package com.gdgoc.arcive.domain.session.service;

import com.gdgoc.arcive.domain.session.entity.Session;
import com.gdgoc.arcive.domain.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SessionCacheService {

    private final SessionRepository sessionRepository;

    @Cacheable(value = "sessions", key = "'all'")
    public List<Session> getAllSessionsCached() {
        return sessionRepository.findAll();
    }
}