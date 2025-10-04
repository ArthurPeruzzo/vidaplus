package com.uninter.vidaplus.security.core.gateway.mapper.role;

import com.uninter.vidaplus.security.core.domain.Role;
import com.uninter.vidaplus.security.core.gateway.entity.RoleEntity;

public interface RoleMapper {

    Role entityToDomain(RoleEntity entity);
}
