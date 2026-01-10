package com.gdgoc.arcive.domain.part.service;

import com.gdgoc.arcive.domain.part.dto.PartResponse;
import com.gdgoc.arcive.domain.part.dto.SimplePartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PartService {

    private final PartCacheService partCacheService;

    public List<SimplePartResponse> getAllSimpleParts() {
        return partCacheService.getAllPartsCached().stream()
                .map(SimplePartResponse::from)
                .toList();
    }

    public List<PartResponse> getAllParts() {
        return partCacheService.getAllPartsCached().stream()
                .map(PartResponse::from)
                .toList();
    }
}