package com.gdgoc.arcive.domain.activity.service;

import com.gdgoc.arcive.domain.activity.dto.ActivityResponse;
import com.gdgoc.arcive.domain.activity.dto.SimpleActivityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityCacheService activityCacheService;

    public List<SimpleActivityResponse> getAllSimpleActivities() {
        return activityCacheService.getAllActivitiesCached().stream()
                .map(SimpleActivityResponse::from)
                .toList();
    }

    public List<ActivityResponse> getAllActivities() {
        return activityCacheService.getAllActivitiesCached().stream()
                .map(ActivityResponse::from)
                .toList();
    }
}