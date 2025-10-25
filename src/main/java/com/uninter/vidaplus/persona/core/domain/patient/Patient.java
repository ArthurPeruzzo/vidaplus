package com.uninter.vidaplus.persona.core.domain.patient;

import com.uninter.vidaplus.persona.core.domain.Persona;
import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;
import lombok.Getter;

public class Patient extends Persona {

    //TODO adicionar mais campos ao paciente
    @Getter
    private final Long userId;
    @Getter
    private final SexEnum sex;

    public Patient(Long id, Long userId, String name, String lastName, SexEnum sex, String email) {
        super(id, name, lastName, email);
        this.userId = userId;
        this.sex = sex;
    }
}
