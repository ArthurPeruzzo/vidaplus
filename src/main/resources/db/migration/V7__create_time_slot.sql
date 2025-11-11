CREATE TABLE time_slot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

-- Segunda-feira
INSERT INTO time_slot (day_of_week, start_time, end_time) VALUES
('MONDAY', '08:00:00', '09:00:00'),
('MONDAY', '09:00:00', '10:00:00'),
('MONDAY', '10:00:00', '11:00:00'),
('MONDAY', '11:00:00', '12:00:00'),
('MONDAY', '12:00:00', '13:00:00'),
('MONDAY', '13:00:00', '14:00:00'),
('MONDAY', '14:00:00', '15:00:00'),
('MONDAY', '15:00:00', '16:00:00'),
('MONDAY', '16:00:00', '17:00:00'),
('MONDAY', '17:00:00', '18:00:00');

-- Terça-feira
INSERT INTO time_slot (day_of_week, start_time, end_time) VALUES
('TUESDAY', '08:00:00', '09:00:00'),
('TUESDAY', '09:00:00', '10:00:00'),
('TUESDAY', '10:00:00', '11:00:00'),
('TUESDAY', '11:00:00', '12:00:00'),
('TUESDAY', '12:00:00', '13:00:00'),
('TUESDAY', '13:00:00', '14:00:00'),
('TUESDAY', '14:00:00', '15:00:00'),
('TUESDAY', '15:00:00', '16:00:00'),
('TUESDAY', '16:00:00', '17:00:00'),
('TUESDAY', '17:00:00', '18:00:00');

-- Quarta-feira
INSERT INTO time_slot (day_of_week, start_time, end_time) VALUES
('WEDNESDAY', '08:00:00', '09:00:00'),
('WEDNESDAY', '09:00:00', '10:00:00'),
('WEDNESDAY', '10:00:00', '11:00:00'),
('WEDNESDAY', '11:00:00', '12:00:00'),
('WEDNESDAY', '12:00:00', '13:00:00'),
('WEDNESDAY', '13:00:00', '14:00:00'),
('WEDNESDAY', '14:00:00', '15:00:00'),
('WEDNESDAY', '15:00:00', '16:00:00'),
('WEDNESDAY', '16:00:00', '17:00:00'),
('WEDNESDAY', '17:00:00', '18:00:00');

-- Quinta-feira
INSERT INTO time_slot (day_of_week, start_time, end_time) VALUES
('THURSDAY', '08:00:00', '09:00:00'),
('THURSDAY', '09:00:00', '10:00:00'),
('THURSDAY', '10:00:00', '11:00:00'),
('THURSDAY', '11:00:00', '12:00:00'),
('THURSDAY', '12:00:00', '13:00:00'),
('THURSDAY', '13:00:00', '14:00:00'),
('THURSDAY', '14:00:00', '15:00:00'),
('THURSDAY', '15:00:00', '16:00:00'),
('THURSDAY', '16:00:00', '17:00:00'),
('THURSDAY', '17:00:00', '18:00:00');

-- Sexta-feira
INSERT INTO time_slot (day_of_week, start_time, end_time) VALUES
('FRIDAY', '08:00:00', '09:00:00'),
('FRIDAY', '09:00:00', '10:00:00'),
('FRIDAY', '10:00:00', '11:00:00'),
('FRIDAY', '11:00:00', '12:00:00'),
('FRIDAY', '12:00:00', '13:00:00'),
('FRIDAY', '13:00:00', '14:00:00'),
('FRIDAY', '14:00:00', '15:00:00'),
('FRIDAY', '15:00:00', '16:00:00'),
('FRIDAY', '16:00:00', '17:00:00'),
('FRIDAY', '17:00:00', '18:00:00');