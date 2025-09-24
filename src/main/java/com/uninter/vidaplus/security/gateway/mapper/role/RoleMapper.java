package com.uninter.vidaplus.security.gateway.mapper.role;

import com.uninter.vidaplus.security.domain.Role;
import com.uninter.vidaplus.security.gateway.entity.RoleEntity;

public interface RoleMapper {

    Role entityToDomain(RoleEntity entity);
}
