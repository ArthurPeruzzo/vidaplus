package com.uninter.vidaplus.security.databuilder.entity;

import com.uninter.vidaplus.security.core.gateway.entity.RoleEntity;
import com.uninter.vidaplus.security.core.gateway.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserEntityDataBuilder {
    private Long id = 1L;
    private String email = "default@email.com";
    private String password = "defaultPassword";
    private List<RoleEntity> roles = new ArrayList<>();

    public UserEntityDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntityDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntityDataBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntityDataBuilder withRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserEntity build() {
        return new UserEntity(id, email, password, roles);
    }
}
