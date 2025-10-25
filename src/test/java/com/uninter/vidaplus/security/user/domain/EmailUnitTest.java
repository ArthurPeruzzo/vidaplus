package com.uninter.vidaplus.security.user.domain;

import com.uninter.vidaplus.security.user.core.domain.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailUnitTest {

    @Test
    void shouldValidateInputSuccessFully() {
        String email = "any@email.com";

        Email result = Assertions.assertDoesNotThrow(() -> new Email(email));

        Assertions.assertNotNull(result.value());
    }

    @Test
    void shouldThrowExceptionWhenInvalidInput() {
        String email = "any_email.com";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Email(email));
    }
}
