package com.uninter.vidaplus.persona.infra.controller.patient;

import com.uninter.vidaplus.persona.core.usecase.patient.CreatePatientUseCase;
import com.uninter.vidaplus.persona.infra.controller.patient.dto.PatientCreateDto;
import com.uninter.vidaplus.persona.infra.controller.patient.json.request.PatientCreateRequestJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/patients")
@Tag(name = "Pacientes")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;

    @Operation(
            summary = "Criar um paciente",
            description = "Cria uma novo paciente. Somente um administrador pode criar um paciente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "O email utilizado para a criação já está em uso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PatientCreateRequestJson requestJson) {
        PatientCreateDto dto = new PatientCreateDto(
                requestJson.getName(), requestJson.getLastName(),
                requestJson.getEmail(),requestJson.getSex(),
                requestJson.getHealthcareFacilityId(),
                requestJson.getPassword());

        createPatientUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
