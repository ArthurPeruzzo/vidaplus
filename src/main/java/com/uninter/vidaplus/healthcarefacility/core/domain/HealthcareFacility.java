package com.uninter.vidaplus.healthcarefacility.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthcareFacility {
    private Long id;
    private String name;
    private Cnpj cnpj;

    public HealthcareFacility(String name, String cnpj) {
        this.name = name;
        this.cnpj = new Cnpj(cnpj);
    }

    public HealthcareFacility(Long healthcareFacilityId) {
        this.id = healthcareFacilityId;
    }
}
