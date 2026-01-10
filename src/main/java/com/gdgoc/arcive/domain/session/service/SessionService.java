package com.gdgoc.arcive.domain.session.service;

import com.gdgoc.arcive.domain.session.dto.SessionResponse;
import com.gdgoc.arcive.infra.s3.config.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SessionService {

    private final SessionCacheService sessionCacheService;
    private final S3Properties s3Properties;

    public List<SessionResponse> getAllSessions() {
        String urlPrefix = s3Properties.getS3().getUrlPrefix();
        return sessionCacheService.getAllSessionsCached().stream()
                .map(session -> SessionResponse.from(session, urlPrefix))
                .toList();
    }
}