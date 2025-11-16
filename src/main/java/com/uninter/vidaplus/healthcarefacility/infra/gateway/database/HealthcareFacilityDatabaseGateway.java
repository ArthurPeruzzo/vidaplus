package com.uninter.vidaplus.healthcarefacility.infra.gateway.database;

import com.uninter.vidaplus.healthcarefacility.core.domain.Cnpj;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.exception.HealthcareFacilityAlreadyCreatedException;
import com.uninter.vidaplus.healthcarefacility.core.gateway.HealthcareFacilityGateway;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.reposioty.HealthcareFacilityRepository;
import com.uninter.vidaplus.infra.exception.ErrorAccessDatabaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        } catch (DataIntegrityViolationException e) {
            log.error("Já existe uma unidade hospitalar com o mesmo cnpj", e);
            throw new HealthcareFacilityAlreadyCreatedException("CNPJ já utilizado. Informe um novo CNPJ");
        } catch (Exception e) {
            log.error("Erro ao salvar unidade hospitalar", e);
            throw new ErrorAccessDatabaseException();
        }

    }

    @Override
    public Page<HealthcareFacility> findByParams(int page, int pageSize) {
        try {
            Pageable pageRequest = PageRequest.of(page, pageSize);
            return healthcareFacilityRepository.findAll(pageRequest)
                    .map(entity ->
                            new HealthcareFacility(entity.getId(), entity.getName(), new Cnpj(entity.getCnpj())));
        } catch (Exception e) {
            log.error("Erro ao buscar unidades hospitalares", e);
            throw new ErrorAccessDatabaseException();
        }
    }
}
