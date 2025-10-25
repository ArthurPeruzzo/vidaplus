package com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity;

import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public HealthcareProfessionalEntity(String name, String lastName, HealthcareProfessionalPosition position, UserEntity userEntity) {
        this.name = name;
        this.lastName = lastName;
        this.user = userEntity;
        this.position = position;
    }
}
