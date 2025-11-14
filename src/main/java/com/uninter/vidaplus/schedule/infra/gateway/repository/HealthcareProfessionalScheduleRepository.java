package com.uninter.vidaplus.schedule.infra.gateway.repository;

import com.uninter.vidaplus.schedule.infra.gateway.entity.HealthcareProfessionalScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HealthcareProfessionalScheduleRepository extends JpaRepository<HealthcareProfessionalScheduleEntity, Long> {

    @Query("""
    SELECT hps
    FROM HealthcareProfessionalScheduleEntity hps
    JOIN hps.timeSlot ts
    WHERE hps.healthcareProfessional.id = :professionalId
      AND hps.healthcareFacility.id = :facilityId
      AND ts.dayOfWeek = FUNCTION('DAYNAME', :dateTime)
      AND FUNCTION('TIME', :dateTime) >= ts.startTime
      AND FUNCTION('TIME', :dateTime) < ts.endTime
    """)
    Optional<HealthcareProfessionalScheduleEntity> findByProfessionalAndFacilityAndDateTime(
            @Param("professionalId") Long professionalId,
            @Param("facilityId") Long facilityId,
            @Param("dateTime") LocalDateTime dateTime
    );


}
