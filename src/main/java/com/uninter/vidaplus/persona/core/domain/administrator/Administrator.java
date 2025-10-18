package com.uninter.vidaplus.persona.core.domain.administrator;

import com.uninter.vidaplus.persona.core.domain.Persona;
import lombok.Getter;

public class Administrator extends Persona {
    @Getter
    private Long userId;

    public Administrator(Long id, Long userId, String name, String lastName, String email) {
        super(id, name, lastName, email);
        this.userId = userId;
    }
}
