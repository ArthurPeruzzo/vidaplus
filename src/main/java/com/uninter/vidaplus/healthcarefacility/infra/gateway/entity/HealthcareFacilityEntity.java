package com.uninter.vidaplus.healthcarefacility.infra.gateway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="healthcare_facility")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HealthcareFacilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    public HealthcareFacilityEntity(Long healthcareFacilityId) {
        this.id = healthcareFacilityId;
    }
}
