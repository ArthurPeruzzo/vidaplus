package com.uninter.vidaplus.schedule.infra.gateway.repository;

import com.uninter.vidaplus.schedule.infra.gateway.entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
}
