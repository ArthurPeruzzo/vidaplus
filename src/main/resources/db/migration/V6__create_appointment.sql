CREATE TABLE IF NOT EXISTS `appointment` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `healthcare_facility_id` bigint NOT NULL,
    `healthcare_professional_id` bigint NOT NULL,
    `patient_id` bigint NOT NULL,
    `date` TIMESTAMP NOT NULL,
    `date_created` TIMESTAMP NOT NULL,
    `type` varchar(100) NOT NULL,
    `status` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_healthcare_facility_appointment` FOREIGN KEY (`healthcare_facility_id`) REFERENCES `healthcare_facility` (`id`),
    CONSTRAINT `fk_healthcare_professional_appointment` FOREIGN KEY (`healthcare_professional_id`) REFERENCES `healthcare_professional` (`id`),
    CONSTRAINT `fk_patient_appointment` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
);