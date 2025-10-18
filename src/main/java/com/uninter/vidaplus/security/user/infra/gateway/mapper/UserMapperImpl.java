package com.uninter.vidaplus.security.user.infra.gateway.mapper;

import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.gateway.mapper.RoleMapper;
import com.uninter.vidaplus.security.user.core.gateway.mapper.UserMapper;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final RoleMapper roleMapper;

    @Override
    public User entityToDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRoles().stream().map(roleMapper::entityToDomain).toList());
    }

    @Override
    public UserEntity domainToEntity(User user) {
        return new UserEntity(user.getEmail(), user.getPassword(),
                user.getRoles().stream().map(roleMapper::domainToEntity).toList());
    }
}
