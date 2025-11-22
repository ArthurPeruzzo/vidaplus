package com.uninter.vidaplus.healthcarefacility.unit;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.gateway.HealthcareFacilityGateway;
import com.uninter.vidaplus.healthcarefacility.core.usecase.CreateHealthcareFacilityUseCase;
import com.uninter.vidaplus.healthcarefacility.infra.controller.dto.HealthcareFacilityCreateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateHealthcareFacilityUseCaseUnitTest {

    @InjectMocks
    private CreateHealthcareFacilityUseCase useCase;

    @Mock
    private HealthcareFacilityGateway gateway;

    @Test
    void shouldCreateSuccessFully() {
        HealthcareFacilityCreateDto healthcareFacilityCreateDto = new HealthcareFacilityCreateDto("any", "26.793.920/0001-06");
        Assertions.assertDoesNotThrow(()-> useCase.create(healthcareFacilityCreateDto));
        Mockito.verify(gateway).create(Mockito.any(HealthcareFacility.class));

    }
}
