package com.uninter.vidaplus.security.gateway.mapper.role;

import com.uninter.vidaplus.security.domain.Role;
import com.uninter.vidaplus.security.gateway.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements RoleMapper {
    @Override
    public Role entityToDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }
}
