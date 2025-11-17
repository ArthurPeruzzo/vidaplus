package com.uninter.vidaplus.persona.infra.gateway.patient.repository;

import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByUser_Id(Long id);
}
