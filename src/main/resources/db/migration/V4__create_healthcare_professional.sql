CREATE TABLE IF NOT EXISTS `healthcare_professional` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `position` varchar(255) NOT NULL,
    `user_id` bigint NOT NULL,
    `healthcare_facility_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_user_healthcare_professional` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_healthcare_facility_healthcare_professional` FOREIGN KEY (`healthcare_facility_id`) REFERENCES `healthcare_facility` (`id`)
);