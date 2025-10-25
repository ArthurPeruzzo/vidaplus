package com.uninter.vidaplus.security.user.core.domain.factory.administrator;

import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserFactory;

public class UserAdministratorFactory extends UserFactory {
    @Override
    public UserCreator create() {
        return new UserAdministrator();
    }
}
