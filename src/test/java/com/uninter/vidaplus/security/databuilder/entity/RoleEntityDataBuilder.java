package com.uninter.vidaplus.security.databuilder.entity;

import com.uninter.vidaplus.security.core.domain.RoleEnum;
import com.uninter.vidaplus.security.core.gateway.entity.RoleEntity;

public class RoleEntityDataBuilder {

    private Long id = 1L;
    private final RoleEnum role = RoleEnum.ROLE_ADMINISTRATOR;

    public RoleEntityDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public RoleEntity build() {
        return new RoleEntity(id, role);
    }
}
