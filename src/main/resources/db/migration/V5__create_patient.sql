CREATE TABLE IF NOT EXISTS `patient` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `sex` varchar(255) NOT NULL,
    `user_id` bigint NOT NULL,
    `healthcare_facility_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_user_patient` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_healthcare_facility_patient` FOREIGN KEY (`healthcare_facility_id`) REFERENCES `healthcare_facility` (`id`)
);