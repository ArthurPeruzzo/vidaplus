package com.uninter.vidaplus.security.databuilder.domain;

import com.uninter.vidaplus.security.user.core.domain.Email;
import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.password.PasswordBase;
import com.uninter.vidaplus.security.user.core.domain.password.PasswordHash;

import java.util.ArrayList;
import java.util.List;

public class UserDataBuilder {
    private Long id = 1L;
    private Email email = new Email("default@email.com");
    private PasswordBase password = new PasswordHash("any-hash");
    private List<Role> roles = new ArrayList<>();

    public UserDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserDataBuilder withEmail(Email email) {
        this.email = email;
        return this;
    }

    public UserDataBuilder withPassword(PasswordBase password) {
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
