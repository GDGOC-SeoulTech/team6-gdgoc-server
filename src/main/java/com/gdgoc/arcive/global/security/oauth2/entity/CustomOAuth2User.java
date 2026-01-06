package com.gdgoc.arcive.global.security.oauth2.entity;

import com.gdgoc.arcive.domain.member.entity.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final Long id;
    private final Role role;
    private final boolean isNew;

    public CustomOAuth2User(String email, Long id, Role role, boolean isNew) {
        super(Collections.singletonList(new SimpleGrantedAuthority(role.name())),
                Map.of("email", email),
                "email");
        this.email = email;
        this.id = id;
        this.role = role;
        this.isNew = isNew;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}