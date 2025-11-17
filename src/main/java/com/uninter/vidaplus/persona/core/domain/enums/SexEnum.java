package com.uninter.vidaplus.persona.core.domain.enums;

import lombok.Getter;

@Getter
public enum SexEnum {
    FEMALE("Feminino"),
    MALE("Masculino");

    private final String description;


    SexEnum(String description) {
        this.description = description;
    }
}
