package com.uninter.vidaplus.healthcarefacility.core.usecase;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.gateway.HealthcareFacilityGateway;
import com.uninter.vidaplus.healthcarefacility.infra.controller.dto.HealthcareFacilityCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHealthcareFacilityUseCase {

    private final HealthcareFacilityGateway healthcareFacilityGateway;

    public void create(HealthcareFacilityCreateDto dto) {
        HealthcareFacility healthcareFacility = new HealthcareFacility(dto.name(), dto.cnpj());
        healthcareFacilityGateway.create(healthcareFacility);
    }
}
