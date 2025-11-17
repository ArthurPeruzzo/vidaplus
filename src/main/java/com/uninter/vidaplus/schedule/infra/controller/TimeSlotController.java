package com.uninter.vidaplus.schedule.infra.controller;

import com.uninter.vidaplus.schedule.core.usecase.CreateAllHealthcareProfessionalScheduleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/time-slots")
public class TimeSlotController {

    private final CreateAllHealthcareProfessionalScheduleUseCase createAllHealthcareProfessionalScheduleUseCase;

    @PostMapping
    public ResponseEntity<HttpStatus> createAllHealthcareProfessionalSchedule() {
        createAllHealthcareProfessionalScheduleUseCase.createAllSchedules();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
