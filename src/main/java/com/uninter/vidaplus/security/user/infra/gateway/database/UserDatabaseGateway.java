package com.uninter.vidaplus.security.user.infra.gateway.database;

import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import com.uninter.vidaplus.security.user.core.gateway.mapper.UserMapper;
import com.uninter.vidaplus.security.user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDatabaseGateway implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .map(userMapper::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar usuario por email: {}", email, e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
