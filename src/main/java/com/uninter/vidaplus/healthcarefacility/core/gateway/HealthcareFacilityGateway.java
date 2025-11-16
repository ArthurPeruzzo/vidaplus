package com.uninter.vidaplus.healthcarefacility.core.gateway;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import org.springframework.data.domain.Page;

public interface HealthcareFacilityGateway {

    void create(HealthcareFacility healthcareFacility);
    Page<HealthcareFacility> findByParams(int page, int pageSize);
}
