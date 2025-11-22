package com.uninter.vidaplus.healthcarefacility.unit;

import com.uninter.vidaplus.healthcarefacility.core.domain.Cnpj;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.exception.HealthcareFacilityAlreadyCreatedException;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.database.HealthcareFacilityDatabaseGateway;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.reposioty.HealthcareFacilityRepository;
import com.uninter.vidaplus.infra.exception.ErrorAccessDatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class HealthcareFacilityDatabaseGatewayUnitTest {

    @InjectMocks
    private HealthcareFacilityDatabaseGateway gateway;

    @Mock
    private HealthcareFacilityRepository repository;

    @Test
    void shouldCreateSuccessFully() {
        HealthcareFacility healthcareFacility = new HealthcareFacility(1L, "any", new Cnpj("80932849032"));

        Assertions.assertDoesNotThrow(()-> gateway.create(healthcareFacility));

        Mockito.verify(repository).save(Mockito.any(HealthcareFacilityEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenHealthcareFacilityAlreadyExists() {
        HealthcareFacility healthcareFacility = new HealthcareFacility(1L, "any", new Cnpj("80932849032"));

        Mockito.when(repository.save(Mockito.any(HealthcareFacilityEntity.class))).thenThrow(new RuntimeException("Error"));

        var exception = Assertions.assertThrows(ErrorAccessDatabaseException.class, () -> gateway.create(healthcareFacility));

        Mockito.verify(repository).save(Mockito.any(HealthcareFacilityEntity.class));

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals("Erro ao acessar base de dados", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreateHealthcareFacilityFails() {
        HealthcareFacility healthcareFacility = new HealthcareFacility(1L, "any", new Cnpj("80932849032"));

        Mockito.when(repository.save(Mockito.any(HealthcareFacilityEntity.class))).thenThrow(new DataIntegrityViolationException("Error"));

        var exception = Assertions.assertThrows(HealthcareFacilityAlreadyCreatedException.class, () -> gateway.create(healthcareFacility));

        Mockito.verify(repository).save(Mockito.any(HealthcareFacilityEntity.class));

        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        Assertions.assertEquals("CNPJ já utilizado. Informe um novo CNPJ", exception.getMessage());
    }

    @Test
    void shouldFindByParamsSuccessfully() {
        HealthcareFacilityEntity entity = new HealthcareFacilityEntity(1L, "Hospital A", "12345678000190");
        Page<HealthcareFacilityEntity> pageResult = new PageImpl<>(List.of(entity));

        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(pageResult);

        Page<HealthcareFacility> result = gateway.findByParams(0, 10);

        Mockito.verify(repository).findAll(Mockito.any(Pageable.class));

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertNotNull(result.getContent());
        Assertions.assertEquals("Hospital A", result.getContent().getFirst().getName());
        Assertions.assertEquals("12345678000190", result.getContent().getFirst().getCnpj().getValue());
    }

    @Test
    void shouldThrowExceptionWhenFindByParamsFails() {
        Mockito.when(repository.findAll(Mockito.any(Pageable.class)))
                .thenThrow(new RuntimeException("Error"));

        var exception = Assertions.assertThrows(ErrorAccessDatabaseException.class,
                () -> gateway.findByParams(0, 10));

        Mockito.verify(repository).findAll(Mockito.any(Pageable.class));

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals("Erro ao acessar base de dados", exception.getMessage());
    }

}
