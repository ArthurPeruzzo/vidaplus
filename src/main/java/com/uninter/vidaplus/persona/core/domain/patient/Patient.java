package com.uninter.vidaplus.persona.core.domain.patient;

import com.uninter.vidaplus.persona.core.domain.Persona;
import lombok.Getter;

public class Patient extends Persona {

    //TODO adicionar mais campos ao paciente
    @Getter
    private Long userId;

    public Patient(Long id, Long userId, String name, String lastName, String email) {
        super(id, name, lastName, email);
        this.userId = userId;
    }
}
