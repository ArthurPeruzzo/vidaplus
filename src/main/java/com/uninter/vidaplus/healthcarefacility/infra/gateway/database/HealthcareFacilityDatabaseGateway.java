package com.uninter.vidaplus.healthcarefacility.infra.gateway.database;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.gateway.HealthcareFacilityGateway;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.reposioty.HealthcareFacilityRepository;
import com.uninter.vidaplus.security.user.core.exception.ErrorAccessDatabaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HealthcareFacilityDatabaseGateway implements HealthcareFacilityGateway {

    private final HealthcareFacilityRepository healthcareFacilityRepository;

    @Override
    public void create(HealthcareFacility healthcareFacility) {
        try {
            HealthcareFacilityEntity entity = new HealthcareFacilityEntity(
                    healthcareFacility.getId(),
                    healthcareFacility.getName(),
                    healthcareFacility.getCnpj().getValue());

            healthcareFacilityRepository.save(entity);
        } catch (Exception e) {
            log.error("Erro ao salvar unidade hospitalar", e);
            throw new ErrorAccessDatabaseException();
        }

    }
}
