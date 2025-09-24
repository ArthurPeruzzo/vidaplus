package com.uninter.vidaplus.security.gateway.database;

import com.uninter.vidaplus.security.domain.User;

import java.util.Optional;

public interface UserGateway {

    Optional<User> findByEmail(String email);
}
