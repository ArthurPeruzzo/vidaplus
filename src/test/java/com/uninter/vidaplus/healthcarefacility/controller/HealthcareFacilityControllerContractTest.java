package com.uninter.vidaplus.healthcarefacility.controller;

import com.uninter.vidaplus.healthcarefacility.core.domain.Cnpj;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.usecase.CreateHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.core.usecase.FindHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.infra.controller.HealthcareFacilityController;
import com.uninter.vidaplus.healthcarefacility.infra.controller.dto.HealthcareFacilityCreateDto;
import com.uninter.vidaplus.resources.NoSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("controller-test")
@ImportAutoConfiguration(NoSecurityConfiguration.class)
@WebMvcTest(controllers = HealthcareFacilityController.class)
class HealthcareFacilityControllerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateHealthcareFacilityUseCase createHealthcareFacilityUseCase;

    @MockitoBean
    private FindHealthcareFacilityUseCase findHealthcareFacilityUseCase;

    @ParameterizedTest
    @CsvSource({
            "'{\"name\":\"\",\"cnpj\":\"99.607.784/0001-88\"}', name, 'O nome deve ser preenchido'",
            "'{\"name\":\"Teste\",\"cnpj\":\"996077840001\"}', cnpj, 'O conteúdo ou a formatação do cnpj não é válida. Deve ser seguido o seguinte formato de exemplo: 00.000.000/0000-00 ou sem formatação'"
    })
    void shouldReturn400WithValidationMessage(String requestJson, String field, String expectedMessage) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/healthcare-facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$." + field).value(expectedMessage));

        Mockito.verifyNoInteractions(createHealthcareFacilityUseCase);
    }

    @Test
    void shouldReturn200WhenParamsValid() throws Exception {
        String json = """
                {
                  "name": "any-name",
                  "cnpj": "99.607.784/0001-88"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/healthcare-facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(createHealthcareFacilityUseCase).create(Mockito.any(HealthcareFacilityCreateDto.class));
    }

    @Test
    void shouldReturn200WhenParamsValid_findAllHealthcareFacilities() throws Exception {
        int page = 0;
        int pageSize = 10;
        String response = """
                {
                     "content": [
                         {
                             "id": 1,
                             "name": "Santa Maria",
                             "cnpj": "26793920000106"
                         }
                     ],
                     "page": 0,
                     "size": 10,
                     "totalElements": 1,
                     "totalPages": 1
                 }
                """;

        List<HealthcareFacility> healthcareFacilities = List.of(new HealthcareFacility(1L, "Santa Maria", new Cnpj("26793920000106")));
        Page<HealthcareFacility> result = new PageImpl<>(healthcareFacilities, PageRequest.of(page, pageSize), 1);
        Mockito.when(findHealthcareFacilityUseCase.findByParams(page, pageSize)).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/healthcare-facilities")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(response));

        Mockito.verify(findHealthcareFacilityUseCase).findByParams(page, pageSize);
    }

}
