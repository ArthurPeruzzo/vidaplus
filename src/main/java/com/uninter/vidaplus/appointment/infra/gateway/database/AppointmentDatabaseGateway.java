package com.uninter.vidaplus.appointment.infra.gateway.database;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import com.uninter.vidaplus.appointment.infra.gateway.repository.AppointmentRepository;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppointmentDatabaseGateway implements AppointmentGateway {

    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public void create(Appointment appointment) {
        try {
            Long healthcareFacilityId = appointment.getHealthcareFacilityId();
            Long healthcareProfessionalId = appointment.getHealthcareProfessionalId();
            Long patientId = appointment.getPatientId();

            HealthcareFacilityEntity healthcareFacilityEntity = new HealthcareFacilityEntity(healthcareFacilityId);
            HealthcareProfessionalEntity healthcareProfessionalEntity = new HealthcareProfessionalEntity(healthcareProfessionalId);
            PatientEntity patientEntity = new PatientEntity(patientId);

            AppointmentEntity appointmentEntity = new AppointmentEntity(appointment.getId(), appointment.getStatus(), appointment.getType(),
                    appointment.getDate(), appointment.getDateCreated(), healthcareFacilityEntity, healthcareProfessionalEntity, patientEntity);

            appointmentRepository.save(appointmentEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
