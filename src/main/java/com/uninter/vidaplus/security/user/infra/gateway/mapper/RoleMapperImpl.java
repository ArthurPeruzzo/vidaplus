package com.uninter.vidaplus.security.user.infra.gateway.mapper;

import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.gateway.mapper.RoleMapper;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements RoleMapper {
    @Override
    public Role entityToDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    @Override
    public RoleEntity domainToEntity(Role role) {
        return new RoleEntity(role.getName());
    }
}
