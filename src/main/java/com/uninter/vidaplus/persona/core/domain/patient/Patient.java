package com.uninter.vidaplus.persona.core.domain.patient;

import com.uninter.vidaplus.persona.core.domain.Persona;
import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;
import lombok.Getter;

public class Patient extends Persona {

    //TODO adicionar mais campos ao paciente
    @Getter
    private Long userId;
    @Getter
    private SexEnum sex;
    @Getter
    private Long healthcareFacilityId;

    public Patient(Long id, Long userId, Long healthcareFacilityId, String name, String lastName, SexEnum sex, String email) {
        super(id, name, lastName, email);
        this.userId = userId;
        this.sex = sex;
        this.healthcareFacilityId = healthcareFacilityId;
    }

    public Patient(Long patientId) {
        super(patientId);
    }

    public Patient(Long patientId, Long userId) {
        super(patientId);
        this.userId = userId;
    }
}
