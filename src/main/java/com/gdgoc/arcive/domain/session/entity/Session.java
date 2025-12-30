package com.gdgoc.arcive.domain.session.entity;

import com.gdgoc.arcive.domain.part.entity.Part; // 반드시 우리가 만든 Part를 import해야 합니다.
import com.gdgoc.arcive.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer week;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    private Part part; // 이제 이 Part는 우리가 만든 엔티티로 인식됩니다.
}