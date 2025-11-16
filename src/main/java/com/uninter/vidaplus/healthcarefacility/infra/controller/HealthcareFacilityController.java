package com.uninter.vidaplus.healthcarefacility.infra.controller;

import com.uninter.vidaplus.healthcarefacility.core.usecase.CreateHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.core.usecase.FindHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.infra.controller.dto.HealthcareFacilityCreateDto;
import com.uninter.vidaplus.healthcarefacility.infra.controller.json.request.HealthcareFacilityCreateRequestJson;
import com.uninter.vidaplus.healthcarefacility.infra.controller.json.response.HealthcareFacilityResponseJson;
import com.uninter.vidaplus.infra.page.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/healthcare-facilities")
public class HealthcareFacilityController {

    private final CreateHealthcareFacilityUseCase createHealthcareFacilityUseCase;
    private final FindHealthcareFacilityUseCase findHealthcareFacilityUseCase;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid HealthcareFacilityCreateRequestJson requestJson) {
        HealthcareFacilityCreateDto dto = new HealthcareFacilityCreateDto(
                requestJson.name(), requestJson.cnpj());

        createHealthcareFacilityUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<HealthcareFacilityResponseJson>> findAllHealthcareFacilities(@RequestParam(defaultValue = "0") int page,
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
