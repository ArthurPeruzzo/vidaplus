package com.uninter.vidaplus.security.user.core.domain.factory;

import com.uninter.vidaplus.security.user.core.domain.User;

public abstract class UserFactory {

    public abstract UserCreator create();

    public User createUser(UserParams params) {
        UserCreator userCreator = this.create();
        return userCreator.create(params);
    }
}
