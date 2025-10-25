package com.uninter.vidaplus.security.user.core.domain.factory.healthcareprofessional;

import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserFactory;

public class UserHealthcareProfessionalFactory extends UserFactory {
    @Override
    public UserCreator create() {
        return new UserHealthcareProfessional();
    }
}
