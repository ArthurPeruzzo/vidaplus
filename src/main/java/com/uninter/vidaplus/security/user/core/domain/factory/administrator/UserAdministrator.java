package com.uninter.vidaplus.security.user.core.domain.factory.administrator;

import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;

import java.util.List;

public class UserAdministrator implements UserCreator {
    @Override
    public User create(UserParams params) {
        List<Role> roles = createRoles();
        return new User(params.getEmail(), params.getPassword(), roles);
    }

    private List<Role> createRoles() {
        Role role = new Role(RoleEnum.ROLE_ADMINISTRATOR);
        return List.of(role);
    }
}
