package com.uninter.vidaplus.schedule.controller;

import com.uninter.vidaplus.resources.NoSecurityConfiguration;
import com.uninter.vidaplus.schedule.core.usecase.CreateAllHealthcareProfessionalScheduleUseCase;
import com.uninter.vidaplus.schedule.infra.controller.TimeSlotController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
@WebMvcTest(controllers = TimeSlotController.class)
class TimeSlotControllerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateAllHealthcareProfessionalScheduleUseCase useCase;

    @Test
    void shouldCreateAllSchedules() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/time-slots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(useCase).createAllSchedules();
    }


}
