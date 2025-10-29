package com.uninter.vidaplus.healthcarefacility.infra.controller;

import com.uninter.vidaplus.healthcarefacility.core.usecase.CreateHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.infra.controller.dto.HealthcareFacilityCreateDto;
import com.uninter.vidaplus.healthcarefacility.infra.controller.json.request.HealthcareFacilityCreateRequestJson;
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
@RequestMapping(value = "/healthcare-facility")
public class HealthcareFacilityController {

    private final CreateHealthcareFacilityUseCase createHealthcareFacilityUseCase;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid HealthcareFacilityCreateRequestJson requestJson) {
        HealthcareFacilityCreateDto dto = new HealthcareFacilityCreateDto(
                requestJson.name(), requestJson.cnpj());

        createHealthcareFacilityUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
