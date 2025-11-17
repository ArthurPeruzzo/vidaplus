CREATE TABLE IF NOT EXISTS `healthcare_facility` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `cnpj` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `cnpj` (`cnpj`)
);