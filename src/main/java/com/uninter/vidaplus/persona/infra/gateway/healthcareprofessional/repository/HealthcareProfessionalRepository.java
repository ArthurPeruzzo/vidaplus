package com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.repository;

import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthcareProfessionalRepository extends JpaRepository<HealthcareProfessionalEntity, Long> {
}
