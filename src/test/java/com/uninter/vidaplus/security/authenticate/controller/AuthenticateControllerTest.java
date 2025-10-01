package com.uninter.vidaplus.security.authenticate.controller;

import com.uninter.vidaplus.resources.NoSecurityConfiguration;
import com.uninter.vidaplus.security.authenticate.controller.json.request.LoginRequestJson;
import com.uninter.vidaplus.security.authenticate.usecase.AuthenticateUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("controller-test")
@ImportAutoConfiguration(NoSecurityConfiguration.class)
@WebMvcTest(controllers = AuthenticateController.class)
class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticateUserUseCase authenticateUserUseCase;

    @ParameterizedTest
    @CsvSource({
            "'{\"email\":\"\",\"password\":\"123456\"}', email, 'O email deve ser preenchido'",
            "'{\"email\":\"email@email.com\",\"password\":\"\"}', password, 'A senha deve ser preenchida'"
    })
    void shouldReturn400WithValidationMessage(String requestJson, String field, String expectedMessage) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$." + field).value(expectedMessage));
    }

    @Test
    void shouldReturn200WhenParamsValid() throws Exception {
        String json = """
                {
                  "email": "email_3819b21f83a4",
                  "password": "password_72e5137522d8"
                }
                """;

        String token = "any-token";

        Mockito.when(authenticateUserUseCase.authenticate(Mockito.any(LoginRequestJson.class))).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.token").value(token));
    }
}
