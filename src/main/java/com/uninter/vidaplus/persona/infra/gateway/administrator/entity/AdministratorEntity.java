package com.uninter.vidaplus.persona.infra.gateway.administrator.entity;

import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="administrator")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdministratorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public AdministratorEntity(String name, String lastName, UserEntity userEntity) {
        this.name = name;
        this.lastName = lastName;
        this.user = userEntity;
    }
}
