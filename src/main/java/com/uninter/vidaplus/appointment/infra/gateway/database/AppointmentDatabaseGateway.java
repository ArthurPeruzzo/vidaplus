package com.uninter.vidaplus.appointment.infra.gateway.database;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import com.uninter.vidaplus.appointment.infra.gateway.repository.AppointmentRepository;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        try {
            return appointmentRepository.findById(appointmentId)
                    .map(entity -> new Appointment(
                            entity.getId(),
                            entity.getDate(),
                            entity.getDateCreated(),
                            new HealthcareProfessional(entity.getHealthcareProfessionalId()),
                            new Patient(entity.getPatientId()),
                            new HealthcareFacility(entity.getHealthcareFacilityId()),
                            entity.getStatus(),
                            entity.getType()

                    ));
        } catch (Exception e) {
            log.error("Erro ao buscar consulta por id={}", appointmentId, e);
            throw new ErrorAccessDatabaseException();
        }
    }

    @Override
    @Transactional
    public void update(Appointment appointment) {
        try {
            AppointmentEntity entity = appointmentRepository.findById(appointment.getId()).orElseThrow();
            entity.setStatus(appointment.getStatus());
            appointmentRepository.save(entity);
        } catch (Exception e) {
            log.error("Erro ao atualizar consulta ={}", appointment, e);
            throw new ErrorAccessDatabaseException();
        }
    }

    @Override
    public List<Appointment> findByHealthcareProfessionalIdOrPatientIdAndDate(Long healthcareProfessionalId, Long patientId,
                                                                              LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return appointmentRepository
                    .findByHealthcareProfessionalIdOrPatientIdAndDate(startDate, endDate, healthcareProfessionalId, patientId)
                    .stream().map(entity -> new Appointment(
                            entity.getId(),
                            entity.getDate(),
                            entity.getDateCreated(),
                            new HealthcareProfessional(entity.getHealthcareProfessionalId()),
                            new Patient(entity.getPatientId()),
                            new HealthcareFacility(entity.getHealthcareFacilityId()),
                            entity.getStatus(),
                            entity.getType()

                    )).toList();
        } catch (Exception e) {
            log.error("Erro ao buscar consulta healthcareProfessionalId={}, startDate={}, endDate={}", healthcareProfessionalId, startDate, endDate, e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
