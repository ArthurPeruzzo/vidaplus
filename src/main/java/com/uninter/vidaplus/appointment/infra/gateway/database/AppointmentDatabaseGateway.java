package com.uninter.vidaplus.appointment.infra.gateway.database;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.controller.dto.FilterParamsDTO;
import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import com.uninter.vidaplus.appointment.infra.gateway.repository.AppointmentRepository;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;
import com.uninter.vidaplus.schedule.infra.gateway.entity.HealthcareProfessionalScheduleEntity;
import com.uninter.vidaplus.schedule.infra.gateway.entity.TimeSlotEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
            Long patientId = appointment.getPatientId();
            PatientEntity patientEntity = new PatientEntity(patientId);
            HealthcareProfessionalScheduleEntity healthcareProfessionalScheduleEntity = getHealthcareProfessionalScheduleEntity(appointment);

            AppointmentEntity appointmentEntity = new AppointmentEntity(appointment.getId(), appointment.getStatus(), appointment.getType(),
                    appointment.getAppointmentDay(), appointment.getDateCreated(), patientEntity, healthcareProfessionalScheduleEntity);

            appointmentRepository.save(appointmentEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar consulta", e);
            throw new ErrorAccessDatabaseException();
        }
    }

    private static HealthcareProfessionalScheduleEntity getHealthcareProfessionalScheduleEntity(Appointment appointment) {
        HealthcareProfessionalSchedule healthcareProfessionalSchedule = appointment.getHealthcareProfessionalSchedule();
        Long timeSlotId = healthcareProfessionalSchedule.getTimeSlotId();
        TimeSlotEntity timeSlotEntity = new TimeSlotEntity(timeSlotId);

        HealthcareFacilityEntity healthcareFacilityEntity = new HealthcareFacilityEntity(healthcareProfessionalSchedule.getHealthcareFacilityId());
        HealthcareProfessionalEntity healthcareProfessionalEntity = new HealthcareProfessionalEntity(healthcareProfessionalSchedule.getHealthcareProfessionalId());

        return new HealthcareProfessionalScheduleEntity(healthcareProfessionalSchedule.getId(), healthcareProfessionalEntity, healthcareFacilityEntity, timeSlotEntity);
    }

    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        try {
            return appointmentRepository.findById(appointmentId)
                    .map(entity -> new Appointment(
                            entity.getId(),
                            entity.getAppointmentDay(),
                            entity.getDateCreated(),
                            new Patient(entity.getPatientId(), entity.getPatientUserId()),
                            entity.getStatus(),
                            entity.getType(),
                            new HealthcareProfessionalSchedule(
                                    new HealthcareProfessional(entity.getHealthcareProfessionalId(), entity.getHealthcareProfessionalUserId()),
                                    new HealthcareFacility(entity.getHealthcareFacilityId()))
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
                                                                              LocalTime startTime, LocalTime endTime,
                                                                              LocalDate appointmentDay) {
        try {
            return appointmentRepository
                    .findByHealthcareProfessionalIdOrPatientIdAndDate(startTime, endTime, appointmentDay, healthcareProfessionalId, patientId)
                    .stream().map(entity -> new Appointment(
                            entity.getId(),
                            entity.getAppointmentDay(),
                            entity.getDateCreated(),
                            new Patient(entity.getPatientId()),
                            entity.getStatus(),
                            entity.getType(),
                            new HealthcareProfessionalSchedule(
                                    new HealthcareProfessional(entity.getHealthcareProfessionalId()),
                                    new HealthcareFacility(entity.getHealthcareFacilityId()))

                    )).toList();
        } catch (Exception e) {
            log.error("Erro ao buscar consulta healthcareProfessionalId={}, startDate={}, endDate={}", healthcareProfessionalId, startTime, endTime, e);
            throw new ErrorAccessDatabaseException();
        }
    }

    @Override
    public Page<Appointment> findAllPatientAppointmentByUserId(Long userId, FilterParamsDTO params) {
        try {
            Pageable pageable = PageRequest.of(params.page(), params.pageSize());
            return appointmentRepository.findByPatient_User_Id(userId, pageable)
                    .map(entity -> new Appointment(
                    entity.getId(),
                    entity.getAppointmentDay(),
                    entity.getDateCreated(),
                    new Patient(entity.getPatientId(), entity.getPatientUserId(), entity.getPatientName()),
                    entity.getStatus(),
                    entity.getType(),
                            new HealthcareProfessionalSchedule(
                                    new HealthcareProfessional(
                                            entity.getHealthcareProfessionalId(),
                                            entity.getHealthcareProfessionalUserId(),
                                            entity.getHealthcareProfessionalName()),
                                    new HealthcareFacility(entity.getHealthcareFacilityId(), entity.getHealthcareFacilityName()),
                                    new TimeSlot(entity.getDayOfWeekTimeSlot(), entity.getStartTimeTimeSlot(), entity.getEndTimeTimeSlot()))

            ));
        } catch (Exception e) {
            log.error("Erro ao buscar consultas paginadas", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
