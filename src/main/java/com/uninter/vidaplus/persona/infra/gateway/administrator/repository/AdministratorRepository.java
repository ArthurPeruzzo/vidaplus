package com.uninter.vidaplus.persona.infra.gateway.administrator.repository;

import com.uninter.vidaplus.persona.infra.gateway.administrator.entity.AdministratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> {
}
