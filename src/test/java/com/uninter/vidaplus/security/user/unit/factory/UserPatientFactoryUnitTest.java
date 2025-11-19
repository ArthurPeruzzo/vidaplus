package com.uninter.vidaplus.security.user.unit.factory;


import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.factory.patient.UserPatient;
import com.uninter.vidaplus.security.user.core.domain.factory.patient.UserPatientFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPatientFactoryUnitTest {

    @Test
    void shouldReturnUserPatientCreator() {
        UserPatientFactory factory = new UserPatientFactory();

        UserCreator creator = factory.create();

        assertNotNull(creator);
        assertInstanceOf(UserPatient.class, creator);
    }

    @Test
    void shouldCreatePatientUserWithCorrectEmailPasswordAndRole() {
        UserPatientFactory factory = new UserPatientFactory();

        UserParams params = new UserParams("email@email.com", "VidaPlus2025!@#");

        User user = factory.createUser(params);

        assertNotNull(user);
        assertEquals("email@email.com", user.getEmail().value());
        assertEquals("VidaPlus2025!@#", user.getPassword().getValue());

        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertEquals(RoleEnum.ROLE_PATIENT, user.getRoles().getFirst().getName());
    }
}

