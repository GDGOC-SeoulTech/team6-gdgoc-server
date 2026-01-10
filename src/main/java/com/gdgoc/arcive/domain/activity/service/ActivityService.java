package com.gdgoc.arcive.domain.activity.service;

import com.gdgoc.arcive.domain.activity.dto.ActivityResponse;
import com.gdgoc.arcive.domain.activity.dto.SimpleActivityResponse;
import com.gdgoc.arcive.infra.s3.config.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityCacheService activityCacheService;
    private final S3Properties s3Properties;

    public List<SimpleActivityResponse> getAllSimpleActivities() {
        return activityCacheService.getAllActivitiesCached().stream()
                .map(SimpleActivityResponse::from)
                .toList();
    }

    public List<ActivityResponse> getAllActivities() {
        String urlPrefix = s3Properties.getS3().getUrlPrefix();
        return activityCacheService.getAllActivitiesCached().stream()
                .map(activity -> ActivityResponse.from(activity, urlPrefix))
                .toList();
    }
}