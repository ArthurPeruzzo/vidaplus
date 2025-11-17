package com.uninter.vidaplus.persona.infra.gateway.patient.entity;

import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Table(name="patient")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_facility_id", referencedColumnName = "id")
    private HealthcareFacilityEntity healthcareFacility;

    public PatientEntity(String name, String lastName, SexEnum sex, UserEntity userEntity, HealthcareFacilityEntity healthcareFacility) {
        this.name = name;
        this.lastName = lastName;
        this.user = userEntity;
        this.sex = sex;
        this.healthcareFacility = healthcareFacility;
    }

    public PatientEntity(Long patientId) {
        this.id = patientId;
    }

    public Long getUserId() {
        return Optional.of(user).map(UserEntity::getId).orElse(null);
    }

    public Long getHealthcareFacilityId() {
        return Optional.of(healthcareFacility).map(HealthcareFacilityEntity::getId).orElse(null);
    }

    public String getEmail() {
        return Optional.of(user).map(UserEntity::getEmail).orElse(null);
    }
}
