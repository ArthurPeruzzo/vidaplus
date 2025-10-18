package com.uninter.vidaplus.persona.core.domain;

import lombok.Getter;

@Getter
public abstract class Persona {

    private Long id;
    private String name;
    private String lastName;
    private String email; // trocar para value object

    protected Persona(Long id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    protected String getFullName() {
        return name + " " + lastName;
    }
}
