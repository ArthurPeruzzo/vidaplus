package com.uninter.vidaplus.security.core.gateway.mapper.role;

import com.uninter.vidaplus.security.core.domain.Role;
import com.uninter.vidaplus.security.core.gateway.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements RoleMapper {
    @Override
    public Role entityToDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }
}
