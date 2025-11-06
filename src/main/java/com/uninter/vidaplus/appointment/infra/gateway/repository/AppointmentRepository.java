package com.uninter.vidaplus.appointment.infra.gateway.repository;

import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query(value = """
            SELECT a
            FROM AppointmentEntity a
            JOIN a.healthcareProfessional h
            JOIN a.patient p
            WHERE a.status = 'SCHEDULED'
            AND a.date BETWEEN :startDate AND :endDate
            AND (h.id = :healthcareProfessionalId OR p.id = :patientId)
            """)
    List<AppointmentEntity> findByHealthcareProfessionalIdOrPatientIdAndDate(@Param("startDate") LocalDateTime startDate,
                                                                             @Param("endDate") LocalDateTime endDate,
                                                                             @Param("healthcareProfessionalId") Long healthcareProfessionalId,
                                                                             @Param("patientId") Long patientId);

    Page<AppointmentEntity> findByPatient_User_Id(Long id, Pageable pageable);

}
