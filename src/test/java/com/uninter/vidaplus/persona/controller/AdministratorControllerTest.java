package com.uninter.vidaplus.persona.controller;

import com.uninter.vidaplus.persona.core.usecase.administrator.CreateAdministratorUseCase;
import com.uninter.vidaplus.persona.infra.controller.administrator.AdministratorController;
import com.uninter.vidaplus.persona.infra.controller.administrator.dto.AdministratorCreateDto;
import com.uninter.vidaplus.resources.NoSecurityConfiguration;
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
@WebMvcTest(controllers = AdministratorController.class)
class AdministratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateAdministratorUseCase createAdministratorUseCase;

    @ParameterizedTest
    @CsvSource({
            "'{\"name\":\"\",\"lastName\":\"Last\",\"email\":\"email@email.com\",\"password\":\"123456\"}', name, 'O nome deve ser preenchido'",
            "'{\"name\":\"Name\",\"lastName\":\"\",\"email\":\"email@email.com\",\"password\":\"123456\"}', lastName, 'O sobrenome deve ser preenchido'",
            "'{\"name\":\"Name\",\"lastName\":\"Last\",\"email\":\"\",\"password\":\"123456\"}', email, 'O email deve ser preenchido'",
            "'{\"name\":\"Name\",\"lastName\":\"Last\",\"email\":\"email@email.com\",\"password\":\"\"}', password, 'A senha deve ser preenchida'"
    })
    void shouldReturn400WithValidationMessage(String requestJson, String field, String expectedMessage) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/administrators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$." + field).value(expectedMessage));

        Mockito.verifyNoInteractions(createAdministratorUseCase);
    }

    @Test
    void shouldReturn200WhenParamsValid() throws Exception {
        String json = """
                {
                  "name": "any-name",
                  "lastName": "any-lastName",
                  "email": "email@email.com",
                  "password": "password280b2054eb64"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/administrators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(createAdministratorUseCase).create(Mockito.any(AdministratorCreateDto.class));
    }
}
