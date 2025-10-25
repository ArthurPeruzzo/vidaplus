package com.uninter.vidaplus.persona.core.domain.healthcareprofessional;

import com.uninter.vidaplus.persona.core.domain.Persona;
import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import lombok.Getter;

public class HealthcareProfessional extends Persona {
    @Getter
    private final HealthcareProfessionalPosition position;
    @Getter
    private final Long userId;

    public HealthcareProfessional(Long id, Long userId, String name, String lastName, HealthcareProfessionalPosition position, String email) {
        super(id, name, lastName, email);
        this.userId = userId;
        this.position = position;
    }
}
