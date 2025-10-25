package com.uninter.vidaplus.persona.core.usecase.patient;

import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.core.gateway.patient.PatientGateway;
import com.uninter.vidaplus.persona.infra.controller.patient.dto.PatientCreateDto;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.factory.patient.UserPatientFactory;
import com.uninter.vidaplus.security.user.core.domain.password.PasswordHash;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePatientUseCase {

    private final UserGateway userGateway;
    private final PatientGateway patientGateway;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(PatientCreateDto dto) {
        User user = buildUserPatient(dto);
        User userSaved = userGateway.create(user);

        Patient patient = new Patient(null, userSaved.getId(), dto.name(), dto.lastName(), dto.email());
        patientGateway.create(patient);
    }

    private User buildUserPatient(PatientCreateDto dto) {
        UserParams userParams = new UserParams(dto.email(), dto.password());
        User user = new UserPatientFactory().createUser(userParams);
        user.setPassword(new PasswordHash(passwordEncoder.encode(dto.password())));

        return user;
    }
}
