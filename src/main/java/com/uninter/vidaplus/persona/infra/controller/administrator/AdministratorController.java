package com.uninter.vidaplus.persona.infra.controller.administrator;

import com.uninter.vidaplus.persona.core.usecase.administrator.CreateAdministratorUseCase;
import com.uninter.vidaplus.persona.infra.controller.administrator.dto.AdministratorCreateDto;
import com.uninter.vidaplus.persona.infra.controller.administrator.json.request.AdministratorCreateRequestJson;
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
@RequestMapping(value = "/administrators")
@Tag(name = "Administradores")
public class AdministratorController {

    private final CreateAdministratorUseCase createAdministratorUseCase;

    @Operation(
            summary = "Criar um administrador",
            description = "Cria uma novo administrador. Somente um administrador pode criar outro administrador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "O email utilizado para a criação já está em uso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AdministratorCreateRequestJson requestJson) {
        AdministratorCreateDto dto = new AdministratorCreateDto(
                requestJson.name(), requestJson.lastName(),
                requestJson.email(),requestJson.password());

        createAdministratorUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
