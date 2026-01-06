package com.gdgoc.arcive.domain.admin.service;

import com.gdgoc.arcive.domain.activity.entity.Activity;
import com.gdgoc.arcive.domain.activity.exception.ActivityErrorCode;
import com.gdgoc.arcive.domain.activity.exception.ActivityException;
import com.gdgoc.arcive.domain.activity.repository.ActivityRepository;
import com.gdgoc.arcive.domain.member.dto.MemberResponse;
import com.gdgoc.arcive.domain.member.dto.UpdateMemberRoleRequest;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.exception.MemberErrorCode;
import com.gdgoc.arcive.domain.member.exception.MemberException;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import com.gdgoc.arcive.domain.project.dto.CreateProjectRequest;
import com.gdgoc.arcive.domain.project.dto.ProjectResponse;
import com.gdgoc.arcive.domain.project.dto.UpdateProjectRequest;
import com.gdgoc.arcive.domain.project.entity.Project;
import com.gdgoc.arcive.domain.part.dto.PartResponse;
import com.gdgoc.arcive.domain.part.dto.UpdatePartRequest;
import com.gdgoc.arcive.domain.part.entity.Part;
import com.gdgoc.arcive.domain.part.exception.PartErrorCode;
import com.gdgoc.arcive.domain.notification.dto.DiscordNotificationLogResponse;
import com.gdgoc.arcive.domain.notification.entity.DiscordNotificationLog;
import com.gdgoc.arcive.domain.notification.repository.DiscordNotificationLogRepository;
import com.gdgoc.arcive.domain.part.exception.PartException;
import com.gdgoc.arcive.domain.part.repository.PartRepository;
import com.gdgoc.arcive.domain.project.exception.ProjectErrorCode;
import com.gdgoc.arcive.domain.project.exception.ProjectException;
import com.gdgoc.arcive.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;
    private final PartRepository partRepository;
    private final DiscordNotificationLogRepository discordNotificationLogRepository;

    /**
     * 가입 사용자 목록 조회 (승인 대기 포함)
     */
    public List<MemberResponse> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }

    /**
     * 멤버 승인 (PENDING -> MEMBER)
     */
    @Transactional
    public void approveMember(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        
        member.approve();
    }

    /**
     * 멤버 권한 수정
     */
    @Transactional
    public void updateMemberRole(Long userId, UpdateMemberRoleRequest request) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        
        member.updateRole(request.role());
    }

    /**
     * 프로젝트 등록
     */
    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {
        Activity activity = activityRepository.findById(request.activityId())
                .orElseThrow(() -> new ActivityException(ActivityErrorCode.ACTIVITY_NOT_FOUND));

        Project project = Project.builder()
                .projectName(request.projectName())
                .generation(request.generation())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .activity(activity)
                .build();

        Project savedProject = projectRepository.save(project);
        return ProjectResponse.from(savedProject);
    }

    /**
     * 프로젝트 수정
     */
    @Transactional
    public ProjectResponse updateProject(Long projectId, UpdateProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));

        Activity activity = null;
        if (request.activityId() != null) {
            activity = activityRepository.findById(request.activityId())
                    .orElseThrow(() -> new ActivityException(ActivityErrorCode.ACTIVITY_NOT_FOUND));
        }

        project.updateProject(
                request.projectName(),
                request.generation() != null ? request.generation() : project.getGeneration(),
                request.description(),
                request.imageUrl(),
                activity
        );

        return ProjectResponse.from(project);
    }

    /**
     * 프로젝트 삭제
     */
    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));

        projectRepository.delete(project);
    }

    /**
     * 파트 목록 조회
     */
    public List<PartResponse> getAllParts() {
        List<Part> parts = partRepository.findAll();
        return parts.stream()
                .map(PartResponse::from)
                .toList();
    }

    /**
     * 파트 수정
     */
    @Transactional
    public PartResponse updatePart(Long partId, UpdatePartRequest request) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new PartException(PartErrorCode.PART_NOT_FOUND));

        part.updatePart(request.partName(), request.description());

        return PartResponse.from(part);
    }

    /**
     * Discord 알림 로그 목록 조회
     */
    public List<DiscordNotificationLogResponse> getDiscordNotificationLogs() {
        List<DiscordNotificationLog> logs = discordNotificationLogRepository.findAllByOrderByCreatedAtDesc();
        return logs.stream()
                .map(DiscordNotificationLogResponse::from)
                .toList();
    }
}

