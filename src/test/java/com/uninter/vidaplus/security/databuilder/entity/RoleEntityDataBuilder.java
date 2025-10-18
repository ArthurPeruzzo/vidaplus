package com.uninter.vidaplus.security.databuilder.entity;

import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;

public class RoleEntityDataBuilder {

    private Long id = 1L;
    private RoleEnum role = RoleEnum.ROLE_ADMINISTRATOR;

    public RoleEntityDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public RoleEntityDataBuilder withRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    public RoleEntity build() {
        return new RoleEntity(id, role);
    }
}
