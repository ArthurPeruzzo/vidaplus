package com.uninter.vidaplus.persona.infra.gateway.administrator.database;

import com.uninter.vidaplus.persona.core.domain.administrator.Administrator;
import com.uninter.vidaplus.persona.core.gateway.administrator.AdministratorGateway;
import com.uninter.vidaplus.persona.infra.gateway.administrator.entity.AdministratorEntity;
import com.uninter.vidaplus.persona.infra.gateway.administrator.repository.AdministratorRepository;
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
public class AdministratorDatabaseGateway implements AdministratorGateway {

    private final AdministratorRepository administratorRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Administrator administrator) {
        try {
            UserEntity userEntityReference = entityManager.getReference(UserEntity.class, administrator.getUserId());
            AdministratorEntity administratorEntity = new AdministratorEntity(administrator.getName(), administrator.getLastName(), userEntityReference);
            administratorRepository.save(administratorEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar administrador", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
