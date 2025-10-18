package com.uninter.vidaplus.security.user.core.gateway.mapper;

import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;

public interface UserMapper {

    User entityToDomain(UserEntity entity);
    UserEntity domainToEntity(User user);
}
