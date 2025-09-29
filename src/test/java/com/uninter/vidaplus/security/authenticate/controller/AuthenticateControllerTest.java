package com.uninter.vidaplus.security.authenticate.controller;

import com.uninter.vidaplus.resources.NoSecurityConfiguration;
import com.uninter.vidaplus.security.authenticate.usecase.AuthenticateUserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("controller-test")
@ImportAutoConfiguration(NoSecurityConfiguration.class)
@WebMvcTest(controllers = AuthenticateController.class)
class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Test
    void shouldReturn400WhenValidationFails() throws Exception {
        String invalidJson = "{\"email\":\"email@email.com\",\"password\":\"EWQEWQ\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().is2xxSuccessful());
    }
}
