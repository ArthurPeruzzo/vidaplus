CREATE TABLE IF NOT EXISTS `administrator` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_user_administrator` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);