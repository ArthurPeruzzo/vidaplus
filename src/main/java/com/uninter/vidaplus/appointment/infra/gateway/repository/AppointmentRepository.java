package com.uninter.vidaplus.appointment.infra.gateway.repository;

import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
