package com.uninter.vidaplus.schedule.infra.gateway.entity;

import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "healthcare_professional_schedule")
public class HealthcareProfessionalScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_professional_id", referencedColumnName = "id")
    private HealthcareProfessionalEntity healthcareProfessional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_facility_id", referencedColumnName = "id")
    private HealthcareFacilityEntity healthcareFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", referencedColumnName = "id", nullable = false)
    private TimeSlotEntity timeSlot;

}
