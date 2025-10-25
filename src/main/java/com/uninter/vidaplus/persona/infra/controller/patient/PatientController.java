package com.uninter.vidaplus.persona.infra.controller.patient;

import com.uninter.vidaplus.persona.core.usecase.patient.CreatePatientUseCase;
import com.uninter.vidaplus.persona.infra.controller.patient.dto.PatientCreateDto;
import com.uninter.vidaplus.persona.infra.controller.patient.json.request.PatientCreateRequestJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patient")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PatientCreateRequestJson requestJson) {
        PatientCreateDto dto = new PatientCreateDto(
                requestJson.name(), requestJson.lastName(),
                requestJson.email(),requestJson.sex(),
                requestJson.password());

        createPatientUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
