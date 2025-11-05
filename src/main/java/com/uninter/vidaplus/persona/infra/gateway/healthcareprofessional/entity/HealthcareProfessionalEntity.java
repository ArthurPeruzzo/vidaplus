package com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity;

import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Table(name="healthcare_professional")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HealthcareProfessionalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthcareProfessionalPosition position;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_facility_id", referencedColumnName = "id")
    private HealthcareFacilityEntity healthcareFacility;

    public HealthcareProfessionalEntity(String name, String lastName, HealthcareProfessionalPosition position, UserEntity userEntity, HealthcareFacilityEntity healthcareFacility) {
        this.name = name;
        this.lastName = lastName;
        this.user = userEntity;
        this.position = position;
        this.healthcareFacility = healthcareFacility;
    }

    public HealthcareProfessionalEntity(Long healthcareProfessionalId) {
        this.id = healthcareProfessionalId;
    }

    public Long getUserId() {
        return Optional.of(user).map(UserEntity::getId).orElse(null);
    }
}
