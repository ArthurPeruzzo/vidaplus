package com.uninter.vidaplus.healthcarefacility.core.domain;

import lombok.Getter;

import java.io.Serializable;

public class Cnpj implements Serializable {

    @Getter
    private String value;

    public Cnpj(String value) {
        this.value = value.replaceAll("\\D", "");
    }
}
