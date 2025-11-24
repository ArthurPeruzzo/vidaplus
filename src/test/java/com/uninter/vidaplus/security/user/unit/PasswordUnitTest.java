package com.uninter.vidaplus.security.user.unit;

import com.uninter.vidaplus.security.user.core.domain.password.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordUnitTest {

    @Test
    void shouldValidateInputSuccessFully() {
        String password = "anyPassworddddd@123";

        Password result = Assertions.assertDoesNotThrow(() -> new Password(password));

        Assertions.assertNotNull(result.getValue());
    }

    @Test
    void shouldThrowExceptionWhenInvalidInput() {
        String password = "anypassword";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Password(password));
    }
}
