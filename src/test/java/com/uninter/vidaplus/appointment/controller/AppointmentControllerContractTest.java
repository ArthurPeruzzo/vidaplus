package com.uninter.vidaplus.appointment.controller;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import com.uninter.vidaplus.appointment.core.usecase.CancelAppointmentUseCase;
import com.uninter.vidaplus.appointment.core.usecase.CreateAppointmentUseCase;
import com.uninter.vidaplus.appointment.core.usecase.GetAppointmentsByTokenUseCase;
import com.uninter.vidaplus.appointment.infra.controller.AppointmentController;
import com.uninter.vidaplus.appointment.infra.controller.dto.CancelAppointmentDTO;
import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.appointment.infra.controller.dto.FilterParamsDTO;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.resources.NoSecurityConfiguration;
import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("controller-test")
@ImportAutoConfiguration(NoSecurityConfiguration.class)
@WebMvcTest(controllers = AppointmentController.class)
class AppointmentControllerContractTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CreateAppointmentUseCase createAppointmentUseCase;
    @MockitoBean
    private CancelAppointmentUseCase cancelAppointmentUseCase;
    @MockitoBean
    private GetAppointmentsByTokenUseCase getAppointmentsByTokenUseCase;

    @ParameterizedTest
    @CsvSource({
            // date nulo
            "'{\"date\":null,\"healthcareProfessionalId\":2,\"type\":\"IN_PERSON\"}', date, 'Informe a data da consulta'",

            // healthcareProfessionalId nulo
            "'{\"date\":\"18/11/2025 13:00\",\"healthcareProfessionalId\":null,\"type\":\"IN_PERSON\"}', healthcareProfessionalId, 'Informe o profissional de saúde'",

            // type nulo
            "'{\"date\":\"18/11/2025 13:00\",\"healthcareProfessionalId\":2,\"type\":null}', type, 'Informe o tipo do atendimento (IN_PERSON, TELECONSULTATION, HOME_VISIT)'"
    })

    void shouldReturn400WithValidationMessage_createAppointment(String requestJson, String field, String expectedMessage) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$." + field).value(expectedMessage));

        Mockito.verifyNoInteractions(createAppointmentUseCase);
    }

    @Test
    void shouldReturn200WhenParamsValid_createAppointment() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "date": "18/11/2025 13:00",
                  "healthcareProfessionalId": 1,
                  "type": "IN_PERSON"
                }
                """))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(createAppointmentUseCase).create(Mockito.any(CreateAppointmentDTO.class));
    }

    @Test
    void shouldReturn400WithValidationMessage_cancelAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"appointmentId\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$." + "appointmentId").value("Informe o id consulta"));

        Mockito.verifyNoInteractions(cancelAppointmentUseCase);
    }

    @Test
    void shouldReturn200WhenParamsValid_cancelAppointment() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/appointments/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"appointmentId\":1}"))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(cancelAppointmentUseCase).cancel(Mockito.any(CancelAppointmentDTO.class));
    }

    @Test
    void shouldReturn200WhenParamsValid_getAppointments() throws Exception {
        int page = 0;
        int pageSize = 10;
        HealthcareProfessionalSchedule healthcareProfessionalSchedule =
                new HealthcareProfessionalSchedule(
                        new HealthcareProfessional(1L),
                        new HealthcareFacility(1L),
                        new TimeSlot(DayOfWeek.SATURDAY, LocalTime.now(), LocalTime.now()));

        Appointment appointment = new Appointment(1L, LocalDate.now(), LocalDateTime.now(),
                new Patient(1L),
                AppointmentStatus.SCHEDULED, AppointmentType.IN_PERSON, healthcareProfessionalSchedule);

        List<Appointment> appointments = List.of(appointment);
        Page<Appointment> result = new PageImpl<>(appointments, PageRequest.of(page, pageSize), 1);

        Mockito.when(getAppointmentsByTokenUseCase.get(Mockito.any(FilterParamsDTO.class))).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/by")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(getAppointmentsByTokenUseCase).get(Mockito.any(FilterParamsDTO.class));
    }
}

