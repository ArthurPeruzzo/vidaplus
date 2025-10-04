package com.uninter.vidaplus.security.core.gateway.database;

import com.uninter.vidaplus.security.core.domain.User;

import java.util.Optional;

public interface UserGateway {

    Optional<User> findByEmail(String email);
}
