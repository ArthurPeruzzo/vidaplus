CREATE TABLE IF NOT EXISTS `appointment` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `healthcare_professional_schedule_id` bigint NOT NULL,
    `patient_id` bigint NOT NULL,
    `appointment_day` DATE NOT NULL,
    `date_created` TIMESTAMP NOT NULL,
    `type` varchar(100) NOT NULL,
    `status` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_healthcare_professional_schedule_appointment` FOREIGN KEY (`healthcare_professional_schedule_id`) REFERENCES `healthcare_professional_schedule` (`id`),
    CONSTRAINT `fk_patient_appointment` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
    CONSTRAINT uk_appointment_unique UNIQUE (healthcare_professional_schedule_id, appointment_day)
);