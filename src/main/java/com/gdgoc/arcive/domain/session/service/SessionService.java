package com.gdgoc.arcive.domain.session.service;

import com.gdgoc.arcive.domain.session.dto.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SessionService {

    private final SessionCacheService sessionCacheService;

    public List<SessionResponse> getAllSessions() {
        return sessionCacheService.getAllSessionsCached().stream()
                .map(SessionResponse::from)
                .toList();
    }
}