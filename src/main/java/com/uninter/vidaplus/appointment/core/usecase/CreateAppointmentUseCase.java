package com.uninter.vidaplus.appointment.core.usecase;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.domain.rule.ValidateAppointmentsScheduled;
import com.uninter.vidaplus.appointment.core.domain.rule.dto.InputValidateAppointmentScheduled;
import com.uninter.vidaplus.appointment.core.exception.PatientNotFoundAppointmentException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.core.gateway.patient.PatientGateway;
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

    public void create(CreateAppointmentDTO createAppointmentDTO) {
        Long userId = tokenGateway.getUserId();
        Patient patient = findPatientByUserId(userId);
        HealthcareFacility healthcareFacility = createHealthcareFacilityInstance(patient);

        Long healthcareProfessionalId = createAppointmentDTO.healthcareProfessionalId();
        HealthcareProfessional healthcareProfessional = new HealthcareProfessional(healthcareProfessionalId);

        Appointment appointment = new Appointment(createAppointmentDTO.date(), healthcareProfessional, patient, healthcareFacility, createAppointmentDTO.type());

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

    private HealthcareFacility createHealthcareFacilityInstance(Patient patient) {
        Long healthcareFacilityId = patient.getHealthcareFacilityId();
        return new HealthcareFacility(healthcareFacilityId);
    }
}
