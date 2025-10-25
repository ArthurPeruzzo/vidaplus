package com.uninter.vidaplus.persona.infra.gateway.patient.repository;

import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
