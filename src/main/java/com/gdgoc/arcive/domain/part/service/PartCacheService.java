package com.gdgoc.arcive.domain.part.service;

import com.gdgoc.arcive.domain.part.entity.Part;
import com.gdgoc.arcive.domain.part.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PartCacheService {

    private final PartRepository partRepository;

    @Cacheable(value = "parts", key = "'all'")
    public List<Part> getAllPartsCached() {
        return partRepository.findAll();
    }
}
