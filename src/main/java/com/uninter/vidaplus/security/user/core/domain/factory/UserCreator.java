package com.uninter.vidaplus.security.user.core.domain.factory;

import com.uninter.vidaplus.security.user.core.domain.User;

public interface UserCreator {
    User create(UserParams params);
}
