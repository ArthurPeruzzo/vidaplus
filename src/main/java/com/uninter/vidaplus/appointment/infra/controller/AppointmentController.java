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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final GetAppointmentsByTokenUseCase getAppointmentsByTokenUseCase;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateAppointmentRequestJson requestJson) {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(requestJson.date(), requestJson.healthcareProfessionalId(), requestJson.type());

        createAppointmentUseCase.create(createAppointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    //TODO rever o cancelamento
    @PostMapping("/cancel")
    public ResponseEntity<HttpStatus> cancel(@RequestBody @Valid CancelAppointmentRequestJson requestJson) {
        CancelAppointmentDTO cancelAppointmentDTO = new CancelAppointmentDTO(requestJson.appointmentId());

        cancelAppointmentUseCase.cancel(cancelAppointmentDTO);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    //TODO rever pesquisa de busca. Parte de datas e a nova relacao com Appointment HealthcareProfessionalSchedule
    @GetMapping
    public ResponseEntity<PageResponse<AppointmentResponseJson>> getAppointmentsByToken(@RequestParam(defaultValue = "0") int page,
                                                                                        @RequestParam(defaultValue = "10") int pageSize) {
        FilterParamsDTO params = new FilterParamsDTO(page, pageSize);
        Page<AppointmentResponseJson> response = getAppointmentsByTokenUseCase.get(params)
                .map(appointment -> new AppointmentResponseJson(
                        appointment.getId(),
                        appointment.getAppointmentDay().atStartOfDay(), //TODO validar
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
