package com.uninter.vidaplus.persona.infra.gateway.patient.database;

import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.infra.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.core.gateway.patient.PatientGateway;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.repository.PatientRepository;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
            HealthcareFacilityEntity healthcareFacilityReference = new HealthcareFacilityEntity(patient.getHealthcareFacilityId());

            PatientEntity entity = new PatientEntity(patient.getName(), patient.getLastName(), patient.getSex(), userEntityReference, healthcareFacilityReference);
            patientRepository.save(entity);
        } catch (Exception e) {
            log.error("Erro ao salvar paciente", e);
            throw new ErrorAccessDatabaseException();
        }
    }

    @Override
    public Optional<Patient> findByUserId(Long userId) {
        try {
            return patientRepository.findByUser_Id(userId)
                    .map(entity ->
                            new Patient(
                                    entity.getId(),
                                    entity.getUserId(),
                                    entity.getHealthcareFacilityId(),
                                    entity.getName(),
                                    entity.getLastName(),
                                    entity.getSex(),
                                    entity.getEmail()));
        } catch (Exception e) {
            log.error("Erro ao buscar paciente por userId={}", userId, e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
