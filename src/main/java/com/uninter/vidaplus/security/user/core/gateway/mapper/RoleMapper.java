package com.uninter.vidaplus.security.user.core.gateway.mapper;

import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;

public interface RoleMapper {

    Role entityToDomain(RoleEntity entity);
}
