package com.uninter.vidaplus.security.user.core.gateway;

import com.uninter.vidaplus.security.user.core.domain.User;

import java.util.Optional;

public interface UserGateway {

    Optional<User> findByEmail(String email);
}
