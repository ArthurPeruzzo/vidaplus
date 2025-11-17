CREATE TABLE IF NOT EXISTS `healthcare_professional_schedule` (
    `id` BIGINT AUTO_INCREMENT,
    `healthcare_professional_id` bigint NOT NULL,
    `healthcare_facility_id` bigint NOT NULL,
    `time_slot_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_hps_healthcare_professional` FOREIGN KEY (`healthcare_professional_id`) REFERENCES `healthcare_professional` (`id`),
    CONSTRAINT `fk_hps_healthcare_facility` FOREIGN KEY (`healthcare_facility_id`) REFERENCES `healthcare_facility` (`id`),
    CONSTRAINT `fk_hps_time_slot` FOREIGN KEY (`time_slot_id`) REFERENCES `time_slot` (`id`),
    UNIQUE KEY uk_hps_professional_facility_slot (healthcare_professional_id, healthcare_facility_id, time_slot_id)
);