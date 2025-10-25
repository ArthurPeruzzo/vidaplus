package com.uninter.vidaplus.persona.infra.gateway.patient.database;

import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.core.gateway.patient.PatientGateway;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.repository.PatientRepository;
import com.uninter.vidaplus.security.user.core.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PatientDatabaseGateway implements PatientGateway {

    private final PatientRepository patientRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Patient patient) {
        try {
            UserEntity userEntityReference = entityManager.getReference(UserEntity.class, patient.getUserId());
            PatientEntity entity = new PatientEntity(patient.getName(), patient.getLastName(), patient.getSex(), userEntityReference);
            patientRepository.save(entity);
        } catch (Exception e) {
            log.error("Erro ao salvar paciente", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
