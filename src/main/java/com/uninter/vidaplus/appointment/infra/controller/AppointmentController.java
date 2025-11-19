package com.uninter.vidaplus.appointment.infra.controller;

import com.uninter.vidaplus.appointment.core.usecase.CancelAppointmentUseCase;
import com.uninter.vidaplus.appointment.core.usecase.CreateAppointmentUseCase;
import com.uninter.vidaplus.appointment.core.usecase.GetAppointmentsByTokenUseCase;
import com.uninter.vidaplus.appointment.infra.controller.dto.CancelAppointmentDTO;
import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.appointment.infra.controller.dto.FilterParamsDTO;
import com.uninter.vidaplus.appointment.infra.controller.json.request.CancelAppointmentRequestJson;
import com.uninter.vidaplus.appointment.infra.controller.json.request.CreateAppointmentRequestJson;
import com.uninter.vidaplus.appointment.infra.controller.json.response.AppointmentHealthcareFacilityResponseJson;
import com.uninter.vidaplus.appointment.infra.controller.json.response.AppointmentPersonaResponseJson;
import com.uninter.vidaplus.appointment.infra.controller.json.response.AppointmentResponseJson;
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
@Tag(name = "Consultas")
@RequiredArgsConstructor
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final GetAppointmentsByTokenUseCase getAppointmentsByTokenUseCase;

    @Operation(
            summary = "Criar uma consulta",
            description = "Cria uma nova consulta envolvendo um paciente e um profissional. Somente o paciente pode criar consultas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Alguma informação que compõe a criação da consulta não foi encontrado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos" , content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateAppointmentRequestJson requestJson) {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(requestJson.getDate(), requestJson.getHealthcareProfessionalId(), requestJson.getType());

        createAppointmentUseCase.create(createAppointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(
            summary = "Cancelar uma consulta",
            description = "Cancelar uma consulta. Tanto o paciente quanto o profissional podem cancelar a consulta, desde que estejam vinculados a ela"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Alguma informação que compõe o cancelamento da consulta não foi encontrada", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", description = "A consulta já foi cancelada", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos" , content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @PostMapping("/cancel")
    public ResponseEntity<HttpStatus> cancel(@RequestBody @Valid CancelAppointmentRequestJson requestJson) {
        CancelAppointmentDTO cancelAppointmentDTO = new CancelAppointmentDTO(requestJson.appointmentId());

        cancelAppointmentUseCase.cancel(cancelAppointmentDTO);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Operation(
            summary = "Buscar as consultas",
            description = "Busca todas as consultas de forma paginada com base no token informado. Pacientes e profissionais podem visualizar apenas as consultas às quais estão vinculados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultas buscadas com sucesso", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = { @Content(mediaType = "application/json")})
    })
    @GetMapping("/by")
    public ResponseEntity<PageResponse<AppointmentResponseJson>> getAppointmentsByToken(
            @Parameter(description = "Número da página a ser buscada (inicia em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página")
            @RequestParam(defaultValue = "10") int pageSize) {
        FilterParamsDTO params = new FilterParamsDTO(page, pageSize);
        Page<AppointmentResponseJson> response = getAppointmentsByTokenUseCase.get(params)
                .map(appointment -> new AppointmentResponseJson(
                        appointment.getId(),
                        appointment.getAppointmentDay(),
                        appointment.getDayOfWeekTimeSlot(),
                        appointment.getStartTimeTimeSlot(),
                        appointment.getEndTimeTimeSlot(),
                        appointment.getDateCreated(),
                        new AppointmentPersonaResponseJson(appointment.getHealthcareProfessionalId(), appointment.getHealthcareProfessionalName()),
                        new AppointmentPersonaResponseJson(appointment.getPatientId(), appointment.getPatientName()),
                        new AppointmentHealthcareFacilityResponseJson(appointment.getHealthcareFacilityId(), appointment.getHealthcareFacilityName()),
                        appointment.getStatus(),
                        appointment.getType()));

        PageResponse<AppointmentResponseJson> result = PageResponse.from(response);
        return ResponseEntity.ok(result);
    }
}
