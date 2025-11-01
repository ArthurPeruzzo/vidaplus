package com.uninter.vidaplus.persona.infra.controller.healthcareprofessional;

import com.uninter.vidaplus.persona.core.usecase.healthcareprofessional.CreateHealthcareProfessionalUseCase;
import com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.dto.HealthcareProfessionalCreateDto;
import com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.json.request.HealthcareProfessionalCreateRequestJson;
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
@RequestMapping(value = "/healthcare-professionals")
public class HealthcareProfessionalController {

    private final CreateHealthcareProfessionalUseCase createHealthcareProfessionalUseCase;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid HealthcareProfessionalCreateRequestJson requestJson) {
        HealthcareProfessionalCreateDto dto = new HealthcareProfessionalCreateDto(
                requestJson.name(), requestJson.lastName(),
                requestJson.email(),requestJson.position(),
                requestJson.healthcareFacilityId(),
                requestJson.password());

        createHealthcareProfessionalUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
