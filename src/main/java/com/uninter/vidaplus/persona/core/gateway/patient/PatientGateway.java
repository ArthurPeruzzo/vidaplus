package com.uninter.vidaplus.persona.core.gateway.patient;

import com.uninter.vidaplus.persona.core.domain.patient.Patient;

import java.util.Optional;

public interface PatientGateway {

    void create(Patient patient);
    Optional<Patient> findByUserId(Long userId);
}
