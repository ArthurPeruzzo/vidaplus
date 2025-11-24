package com.uninter.vidaplus.schedule.infra.controller;

import com.uninter.vidaplus.schedule.core.usecase.CreateAllHealthcareProfessionalScheduleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/time-slots")
@Tag(name = "Agendas")
public class TimeSlotController {

    private final CreateAllHealthcareProfessionalScheduleUseCase useCase;

    @Operation(
            summary = "Criar agenda do profissional da saúde",
            description = "Cria a agenda completa do profissonal de saúde para uma determinada unidade hospitalar. " +
                    "A agenda cria os horários de segunda à sexta, das 08:00 às 18:00. Somente o próprio profissional pode criar a sua agenda"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agenda criada com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "A agenda completa do profissional já foi criada", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createAllHealthcareProfessionalSchedule() {
        useCase.createAllSchedules();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
