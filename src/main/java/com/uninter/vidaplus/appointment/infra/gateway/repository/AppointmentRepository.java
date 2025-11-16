package com.uninter.vidaplus.appointment.infra.gateway.repository;

import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query(value = """
            SELECT a
            FROM AppointmentEntity a
            JOIN a.healthcareProfessionalSchedule h
            JOIN h.healthcareProfessional hp
            JOIN h.healthcareFacility hf
            JOIN h.timeSlot ts
            JOIN a.patient p
            WHERE a.status = 'SCHEDULED'
            AND a.appointmentDay = :appointmentDay
            AND (
                ts.startTime < :endTime AND ts.endTime > :startTime
            )
            AND (
                hp.id = :healthcareProfessionalId OR p.id = :patientId
            )
            """)
    List<AppointmentEntity> findByHealthcareProfessionalIdOrPatientIdAndDate(@Param("startTime") LocalTime startTime,
                                                                             @Param("endTime") LocalTime endTime,
                                                                             @Param("appointmentDay") LocalDate appointmentDay,
                                                                             @Param("healthcareProfessionalId") Long healthcareProfessionalId,
                                                                             @Param("patientId") Long patientId);

    @Query(value = """
            SELECT a FROM AppointmentEntity a
            JOIN a.patient p
            JOIN p.user u
            WHERE u.id = :userId
            """)
    Page<AppointmentEntity> findByPatient_User_Id(@Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            SELECT a FROM AppointmentEntity a
            JOIN a.healthcareProfessionalSchedule h
            JOIN h.healthcareProfessional hp
            JOIN hp.user u
            WHERE u.id = :userId
            """)
    Page<AppointmentEntity> findByHealthcareProfessionalId_UserId(@Param("userId") Long userId,
                                                           Pageable pageable);

}
