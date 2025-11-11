package com.uninter.vidaplus.persona.core.gateway.healthcareprofessional;

import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;

import java.util.Optional;

public interface HealthcareProfessionalGateway {

    void create(HealthcareProfessional healthcareProfessional);
    Optional<HealthcareProfessional> findByUserId(Long userId);
}
