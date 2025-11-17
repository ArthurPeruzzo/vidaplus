package com.uninter.vidaplus.security.user.core.domain.factory.patient;

import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserFactory;

public class UserPatientFactory extends UserFactory {
    @Override
    public UserCreator create() {
        return new UserPatient();
    }
}
