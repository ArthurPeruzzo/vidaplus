package com.uninter.vidaplus.healthcarefacility.infra.controller;

import com.uninter.vidaplus.healthcarefacility.core.usecase.CreateHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.core.usecase.FindHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.infra.controller.dto.HealthcareFacilityCreateDto;
import com.uninter.vidaplus.healthcarefacility.infra.controller.json.request.HealthcareFacilityCreateRequestJson;
import com.uninter.vidaplus.healthcarefacility.infra.controller.json.response.HealthcareFacilityResponseJson;
import com.uninter.vidaplus.infra.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/healthcare-facilities")
@Tag(name = "Unidades Hospilares")
public class HealthcareFacilityController {

    private final CreateHealthcareFacilityUseCase createHealthcareFacilityUseCase;
    private final FindHealthcareFacilityUseCase findHealthcareFacilityUseCase;

    @Operation(
            summary = "Criar uma unidade hospitalar",
            description = "Cria uma nova unidade hospitalar. Somente um administrador pode criar uma unidade hospitalar"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade hospitalar criada com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Já existe unidade hospitalar com o mesmo CNPJ", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid HealthcareFacilityCreateRequestJson requestJson) {
        HealthcareFacilityCreateDto dto = new HealthcareFacilityCreateDto(
                requestJson.name(), requestJson.cnpj());

        createHealthcareFacilityUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Buscar unidades hospitalares",
            description = "Busca todas as unidades hospitalares de forma paginada. Todas as personas podem buscar as unidades hospitalares"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidades hospitalares buscadas com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<PageResponse<HealthcareFacilityResponseJson>> findAllHealthcareFacilities(
            @Parameter(description = "Número da página a ser buscada (inicia em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página")
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<HealthcareFacilityResponseJson> response = findHealthcareFacilityUseCase.findByParams(page, pageSize).map(domain ->
                new HealthcareFacilityResponseJson(
                        domain.getId(),
                        domain.getName(),
                        domain.getCnpj().getValue()));

        PageResponse<HealthcareFacilityResponseJson> result = PageResponse.from(response);
        return ResponseEntity.ok(result);

    }
}
