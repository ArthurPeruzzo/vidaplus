package com.uninter.vidaplus.security.gateway.database;

import com.uninter.vidaplus.security.domain.User;
import com.uninter.vidaplus.security.gateway.mapper.user.UserMapper;
import com.uninter.vidaplus.security.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
         throw e; //TODO tratar melhor os erros
        }
    }
}
