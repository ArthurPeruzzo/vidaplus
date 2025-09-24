package com.uninter.vidaplus.security.gateway.mapper.user;

import com.uninter.vidaplus.security.domain.User;
import com.uninter.vidaplus.security.gateway.entity.UserEntity;

public interface UserMapper {

    User entityToDomain(UserEntity entity);
}
