package com.uninter.vidaplus.persona.infra.controller.healthcareprofessional;

import com.uninter.vidaplus.persona.core.usecase.healthcareprofessional.CreateHealthcareProfessionalUseCase;
import com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.dto.HealthcareProfessionalCreateDto;
import com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.json.request.HealthcareProfessionalCreateRequestJson;
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
@RequestMapping(value = "/healthcare-professionals")
@Tag(name = "Profissionais da Saúde")
public class HealthcareProfessionalController {

    private final CreateHealthcareProfessionalUseCase createHealthcareProfessionalUseCase;

    @Operation(
            summary = "Criar um profissional da saúde",
            description = "Cria uma novo profissional da saúde. Somente um administrador pode criar um profissional da saúde"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional da saúde criado com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "O email utilizado para a criação já está em uso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid HealthcareProfessionalCreateRequestJson requestJson) {
        HealthcareProfessionalCreateDto dto = new HealthcareProfessionalCreateDto(
                requestJson.getName(), requestJson.getLastName(),
                requestJson.getEmail(),requestJson.getPosition(),
                requestJson.getHealthcareFacilityId(),
                requestJson.getPassword());

        createHealthcareProfessionalUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
