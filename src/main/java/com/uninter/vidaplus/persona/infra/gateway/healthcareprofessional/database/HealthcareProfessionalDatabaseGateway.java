package com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.database;

import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.gateway.healthcareprofessional.HealthcareProfessionalGateway;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.repository.HealthcareProfessionalRepository;
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
public class HealthcareProfessionalDatabaseGateway implements HealthcareProfessionalGateway {

    private final HealthcareProfessionalRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(HealthcareProfessional healthcareProfessional) {
        try {
            UserEntity userEntityReference = entityManager.getReference(UserEntity.class, healthcareProfessional.getUserId());
            HealthcareProfessionalEntity entity = new HealthcareProfessionalEntity(healthcareProfessional.getName(),
                    healthcareProfessional.getLastName(),
                    healthcareProfessional.getPosition(), userEntityReference);

            repository.save(entity);
        } catch (Exception e) {
            log.error("Erro ao salvar Profissional da saude", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
