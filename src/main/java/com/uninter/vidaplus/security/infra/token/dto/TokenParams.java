package com.uninter.vidaplus.security.infra.token.dto;

import java.util.List;

public record TokenParams(Long userId, String email, List<String> roles) {
}
