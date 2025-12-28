package com.gdgoc.arcive.global.security.dto;

import com.gdgoc.arcive.domain.member.entity.Role;

public record TempTokenInfo (String email, Long id, Role role) {
}
