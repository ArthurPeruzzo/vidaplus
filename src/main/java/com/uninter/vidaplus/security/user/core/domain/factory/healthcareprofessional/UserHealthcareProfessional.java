package com.uninter.vidaplus.security.user.core.domain.factory.healthcareprofessional;

import com.uninter.vidaplus.security.user.core.domain.Email;
import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.password.Password;

import java.util.List;

public class UserHealthcareProfessional implements UserCreator {
    @Override
    public User create(UserParams params) {
        List<Role> roles = createRoles();
        return new User(new Email(params.getEmail()), new Password(params.getPassword()), roles);
    }

    private List<Role> createRoles() {
        Role role = new Role(RoleEnum.ROLE_HEALTHCARE_PROFESSIONAL);
        return List.of(role);
    }
}
