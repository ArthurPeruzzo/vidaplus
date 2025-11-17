package com.uninter.vidaplus.healthcarefacility.core.usecase;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.gateway.HealthcareFacilityGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindHealthcareFacilityUseCase {

    private final HealthcareFacilityGateway gateway;

    public Page<HealthcareFacility> findByParams(int page, int pageSize) {
        return gateway.findByParams(page, pageSize);
    }
}
