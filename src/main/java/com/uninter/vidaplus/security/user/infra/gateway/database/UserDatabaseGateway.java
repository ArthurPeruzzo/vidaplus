package com.uninter.vidaplus.security.user.infra.gateway.database;

import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import com.uninter.vidaplus.security.user.core.gateway.mapper.UserMapper;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import com.uninter.vidaplus.security.user.infra.repository.RoleRepository;
import com.uninter.vidaplus.security.user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDatabaseGateway implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

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

    @Override
    public User create(User user) {
        try {
            List<RoleEnum> roleEnums = user.getRoles().stream().map(Role::getName).toList();
            List<RoleEntity> roles = roleRepository.findByNameIn(roleEnums);

            UserEntity userEntity = userMapper.domainToEntity(user);
            userEntity.setRoles(roles);

            UserEntity userSaved = userRepository.save(userEntity);
            return userMapper.entityToDomain(userSaved);
        } catch (Exception e) {
            log.error("Erro ao salvar usuario", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
