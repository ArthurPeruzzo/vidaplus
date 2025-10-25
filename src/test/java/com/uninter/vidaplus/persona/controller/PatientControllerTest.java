package com.uninter.vidaplus.persona.controller;

import com.uninter.vidaplus.persona.core.usecase.patient.CreatePatientUseCase;
import com.uninter.vidaplus.persona.infra.controller.patient.PatientController;
import com.uninter.vidaplus.persona.infra.controller.patient.dto.PatientCreateDto;
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
@WebMvcTest(controllers = PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreatePatientUseCase createPatientUseCase;

    @ParameterizedTest
    @CsvSource({
            "'{\"name\":\"\",\"lastName\":\"Last\",\"email\":\"email@email.com\",\"password\":\"123456\"}', name, 'O nome deve ser preenchido'",
            "'{\"name\":\"Name\",\"lastName\":\"\",\"email\":\"email@email.com\",\"password\":\"123456\"}', lastName, 'O sobrenome deve ser preenchido'",
            "'{\"name\":\"Name\",\"lastName\":\"Last\",\"email\":\"\",\"password\":\"123456\"}', email, 'O email deve ser preenchido'",
            "'{\"name\":\"Name\",\"lastName\":\"Last\",\"email\":\"email@email.com\",\"password\":\"\"}', password, 'A senha deve ser preenchida'",
            "'{\"name\":\"Name\",\"lastName\":\"Last\",\"email\":\"email@email.com\",\"password\":\"123456\"}', sex, 'O sexo deve ser informado (FEMALE/MALE)'",
    })
    void shouldReturn400WithValidationMessage(String requestJson, String field, String expectedMessage) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/patient/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$." + field).value(expectedMessage));

        Mockito.verifyNoInteractions(createPatientUseCase);
    }

    @Test
    void shouldReturn200WhenParamsValid() throws Exception {
        String json = """
                {
                  "name": "any-name",
                  "lastName": "any-lastName",
                  "email": "email@email.com",
                  "sex": "MALE",
                  "password": "password280b2054eb64"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(createPatientUseCase).create(Mockito.any(PatientCreateDto.class));
    }
}
