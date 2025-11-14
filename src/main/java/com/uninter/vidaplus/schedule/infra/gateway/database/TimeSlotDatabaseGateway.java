package com.uninter.vidaplus.schedule.infra.gateway.database;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;
import com.uninter.vidaplus.schedule.core.exception.HealthcareProfessionalScheduleAlreadyExistsException;
import com.uninter.vidaplus.schedule.core.gateway.TimeSlotGateway;
import com.uninter.vidaplus.schedule.infra.gateway.entity.HealthcareProfessionalScheduleEntity;
import com.uninter.vidaplus.schedule.infra.gateway.entity.TimeSlotEntity;
import com.uninter.vidaplus.schedule.infra.gateway.repository.HealthcareProfessionalScheduleRepository;
import com.uninter.vidaplus.schedule.infra.gateway.repository.TimeSlotRepository;
import com.uninter.vidaplus.security.user.core.exception.ErrorAccessDatabaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TimeSlotDatabaseGateway implements TimeSlotGateway {

    private final HealthcareProfessionalScheduleRepository repository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public void createAllSchedule(List<HealthcareProfessionalSchedule> schedules) {
        try {
            List<HealthcareProfessionalScheduleEntity> entities = schedules.stream()
                    .map(schedule ->
                            new HealthcareProfessionalScheduleEntity(
                                    schedule.getId(),
                                    new HealthcareProfessionalEntity(schedule.getHealthcareProfessionalId()),
                                    new HealthcareFacilityEntity(schedule.getHealthcareFacilityId()),
                                    new TimeSlotEntity(schedule.getTimeSlotId()))).toList();
            repository.saveAll(entities);
        } catch (DataIntegrityViolationException e) {
            log.error("A agenda completa do profissional ja foi criada", e);
            throw new HealthcareProfessionalScheduleAlreadyExistsException();
        } catch (Exception e) {
            log.error("Erro ao salvar agenda completa de profissional", e);
            throw new ErrorAccessDatabaseException();
        }
    }

    @Override
    public List<TimeSlot> findAllTimeSlots() {
        try {
            return timeSlotRepository.findAll().stream()
                    .map(entity ->
                            new TimeSlot(
                                    entity.getId(),
                                    entity.getDayOfWeek(),
                                    entity.getStartTime(),
                                    entity.getEndTime()))
                    .toList();
        } catch (Exception e) {
            log.error("Erro ao buscar todos os horarios", e);
            throw new ErrorAccessDatabaseException();
        }
    }

    @Override
    public Optional<HealthcareProfessionalSchedule> findScheduleBy(Long healthcareProfessionalId, Long healthcareFacilityId, LocalDateTime time) {
        try {
            return repository.findByProfessionalAndFacilityAndDateTime(healthcareProfessionalId, healthcareFacilityId, time)
                    .map(entity -> new HealthcareProfessionalSchedule(
                            entity.getId(),
                            new HealthcareProfessional(entity.getHealthcareProfessionalId()),
                            new HealthcareFacility(entity.getHealthcareFacilityId()),
                            new TimeSlot(
                                    entity.getTimeSlotId(),
                                    entity.getTimeSlotDayOfWeek(),
                                    entity.getTimeSlotStartTime(),
                                    entity.getTimeSlotEndTime())
                    ));
        } catch (Exception e) {
            log.error("Erro ao buscar horario por healthcareProfessionalId={}, healthcareFacilityId={}, time={}",healthcareProfessionalId, healthcareFacilityId, time, e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
