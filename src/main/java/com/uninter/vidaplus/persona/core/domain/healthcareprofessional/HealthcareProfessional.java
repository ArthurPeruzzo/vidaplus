package com.uninter.vidaplus.persona.core.domain.healthcareprofessional;

import com.uninter.vidaplus.persona.core.domain.Persona;
import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import lombok.Getter;

public class HealthcareProfessional extends Persona {
    @Getter
    private HealthcareProfessionalPosition position;
    @Getter
    private Long userId;
    @Getter
    private Long healthcareFacilityId;

    public HealthcareProfessional(Long id, Long userId, Long healthcareFacilityId, String name, String lastName, HealthcareProfessionalPosition position, String email) {
        super(id, name, lastName, email);
        this.userId = userId;
        this.position = position;
        this.healthcareFacilityId = healthcareFacilityId;
    }

    public HealthcareProfessional(Long id) {
        super(id);
    }

    public HealthcareProfessional(Long id, Long userId) {
        super(id);
        this.userId = userId;
    }

}
