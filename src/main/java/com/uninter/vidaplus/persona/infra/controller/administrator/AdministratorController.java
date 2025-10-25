package com.uninter.vidaplus.persona.infra.controller.administrator;

import com.uninter.vidaplus.persona.core.usecase.administrator.CreateAdministratorUseCase;
import com.uninter.vidaplus.persona.infra.controller.administrator.dto.AdministratorCreateDto;
import com.uninter.vidaplus.persona.infra.controller.administrator.json.request.AdministratorCreateRequestJson;
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
@RequestMapping(value = "/administrator")
public class AdministratorController {

    private final CreateAdministratorUseCase createAdministratorUseCase;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AdministratorCreateRequestJson requestJson) {
        AdministratorCreateDto dto = new AdministratorCreateDto(
                requestJson.name(), requestJson.lastName(),
                requestJson.email(),requestJson.password());

        createAdministratorUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
