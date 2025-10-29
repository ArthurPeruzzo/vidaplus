package com.uninter.vidaplus.healthcarefacility.infra.gateway.reposioty;

import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthcareFacilityRepository extends JpaRepository<HealthcareFacilityEntity, Long> {
}
