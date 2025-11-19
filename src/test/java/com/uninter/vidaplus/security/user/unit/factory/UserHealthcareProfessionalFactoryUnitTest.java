package com.uninter.vidaplus.security.user.unit.factory;


import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserCreator;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.factory.healthcareprofessional.UserHealthcareProfessional;
import com.uninter.vidaplus.security.user.core.domain.factory.healthcareprofessional.UserHealthcareProfessionalFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserHealthcareProfessionalFactoryUnitTest {

    @Test
    void shouldReturnCreator() {
        UserHealthcareProfessionalFactory factory = new UserHealthcareProfessionalFactory();

        UserCreator creator = factory.create();

        assertNotNull(creator);
        assertInstanceOf(UserHealthcareProfessional.class, creator);
    }

    @Test
    void shouldCreateUserWithCorrectEmailPasswordAndRole() {
        UserHealthcareProfessionalFactory factory = new UserHealthcareProfessionalFactory();

        UserParams params = new UserParams("email@email.com", "VidaPlus2025!@#");

        User user = factory.createUser(params);

        assertNotNull(user);
        assertEquals("email@email.com", user.getEmail().value());
        assertEquals("VidaPlus2025!@#", user.getPassword().getValue());

        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertEquals(RoleEnum.ROLE_HEALTHCARE_PROFESSIONAL, user.getRoles().getFirst().getName());
    }
}

