package com.uninter.vidaplus.security.databuilder.domain;

import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataBuilder {
    private Long id = 1L;
    private String email = "default@email.com";
    private String password = "defaultPassword";
    private List<Role> roles = new ArrayList<>();

    public UserDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDataBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDataBuilder withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public User build() {
        return new User(id, email, password, roles);
    }
}
