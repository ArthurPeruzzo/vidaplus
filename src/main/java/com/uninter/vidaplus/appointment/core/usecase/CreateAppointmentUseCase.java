package com.uninter.vidaplus.appointment.core.usecase;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.domain.rule.ValidateAppointmentsScheduled;
import com.uninter.vidaplus.appointment.core.domain.rule.dto.InputValidateAppointmentScheduled;
import com.uninter.vidaplus.appointment.core.exception.AppointmentHealthcareProfessionalScheduleNotFoundException;
import com.uninter.vidaplus.appointment.core.exception.PatientNotFoundAppointmentException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.core.gateway.patient.PatientGateway;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;
import com.uninter.vidaplus.schedule.core.gateway.TimeSlotGateway;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateAppointmentUseCase {

    private final AppointmentGateway appointmentGateway;
    private final TokenGateway tokenGateway;
    private final PatientGateway patientGateway;
    private final ValidateAppointmentsScheduled validateAppointmentsScheduled;
    private final TimeSlotGateway timeSlotGateway;

    public void create(CreateAppointmentDTO createAppointmentDTO) {
        Long userId = tokenGateway.getUserId();
        Patient patient = findPatientByUserId(userId);
        Appointment appointment = new Appointment(createAppointmentDTO.date(), patient, createAppointmentDTO.type());

        Long healthcareFacilityId = patient.getHealthcareFacilityId();
        Long healthcareProfessionalId = createAppointmentDTO.healthcareProfessionalId();

        findProfessionalScheduleAndAssignSchedule(appointment, healthcareFacilityId, healthcareProfessionalId, createAppointmentDTO);

        InputValidateAppointmentScheduled input = new InputValidateAppointmentScheduled(createAppointmentDTO, healthcareProfessionalId, patient.getId());
        validateAppointmentsScheduled.execute(input);

        appointmentGateway.create(appointment);
    }

    private Patient findPatientByUserId(Long userId) {
        return patientGateway.findByUserId(userId).orElseThrow(() -> {
            log.warn("O paciente nao foi encontrado para o userId={}", userId);
            return new PatientNotFoundAppointmentException();
        });
    }

    private void findProfessionalScheduleAndAssignSchedule(Appointment appointment, Long healthcareFacilityId, Long healthcareProfessionalId, CreateAppointmentDTO createAppointmentDTO) {
        HealthcareProfessionalSchedule healthcareProfessionalSchedule = findProfessionalSchedule(createAppointmentDTO, healthcareProfessionalId, healthcareFacilityId);
        appointment.assignSchedule(healthcareProfessionalSchedule);
    }

    private HealthcareProfessionalSchedule findProfessionalSchedule(CreateAppointmentDTO createAppointmentDTO, Long healthcareProfessionalId, Long healthcareFacilityId) {
        return timeSlotGateway.findScheduleBy(healthcareProfessionalId, healthcareFacilityId, createAppointmentDTO.date()).orElseThrow(() -> {
            log.warn("Agenda do profissional nao encontrada para " +
                    "healthcareProfessionalId = {}, healthcareFacilityId = {}, date = {}", healthcareProfessionalId, healthcareFacilityId, createAppointmentDTO.date());
            return new AppointmentHealthcareProfessionalScheduleNotFoundException();
        });
    }
}
