package com.uninter.vidaplus.security.core.gateway.mapper.user;

import com.uninter.vidaplus.security.core.domain.User;
import com.uninter.vidaplus.security.core.gateway.entity.UserEntity;

public interface UserMapper {

    User entityToDomain(UserEntity entity);
}
