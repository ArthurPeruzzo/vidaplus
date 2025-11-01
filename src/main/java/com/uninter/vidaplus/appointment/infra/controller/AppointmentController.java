package com.uninter.vidaplus.appointment.infra.controller;

import com.uninter.vidaplus.appointment.core.usecase.CancelAppointmentUseCase;
import com.uninter.vidaplus.appointment.core.usecase.CreateAppointmentUseCase;
import com.uninter.vidaplus.appointment.infra.controller.dto.CancelAppointmentDTO;
import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.appointment.infra.controller.json.request.CancelAppointmentRequestJson;
import com.uninter.vidaplus.appointment.infra.controller.json.request.CreateAppointmentRequestJson;
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
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateAppointmentRequestJson requestJson) {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(requestJson.date(), requestJson.healthcareProfessionalId(), requestJson.type());

        createAppointmentUseCase.create(createAppointmentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/cancel")
    public ResponseEntity<HttpStatus> cancel(@RequestBody @Valid CancelAppointmentRequestJson requestJson) {
        CancelAppointmentDTO cancelAppointmentDTO = new CancelAppointmentDTO(requestJson.appointmentId());

        cancelAppointmentUseCase.cancel(cancelAppointmentDTO);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
