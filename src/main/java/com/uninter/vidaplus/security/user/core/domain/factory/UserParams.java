package com.uninter.vidaplus.security.user.core.domain.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserParams {
    private String email;
    private String password;
}
