package com.gdgoc.arcive.domain.activity.service;

import com.gdgoc.arcive.domain.activity.entity.Activity;
import com.gdgoc.arcive.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityCacheService {

    private final ActivityRepository activityRepository;

    @Cacheable(value = "activities", key = "'all'")
    public List<Activity> getAllActivitiesCached() {
        return activityRepository.findAll();
    }
}
