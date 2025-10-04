package com.uninter.vidaplus.security.core.gateway.mapper.user;

import com.uninter.vidaplus.security.core.domain.User;
import com.uninter.vidaplus.security.core.gateway.entity.UserEntity;
import com.uninter.vidaplus.security.core.gateway.mapper.role.RoleMapper;
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
}
