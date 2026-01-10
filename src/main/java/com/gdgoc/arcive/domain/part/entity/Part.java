package com.gdgoc.arcive.domain.part.entity;

import com.gdgoc.arcive.domain.session.entity.Session;
import com.gdgoc.arcive.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Part extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Long id;

    @Column(name = "part_name", nullable = false, length = 50)
    private String partName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL)
    private List<Session> sessions = new ArrayList<>();
}