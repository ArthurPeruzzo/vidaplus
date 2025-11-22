package com.uninter.vidaplus.healthcarefacility.unit;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.healthcarefacility.core.gateway.HealthcareFacilityGateway;
import com.uninter.vidaplus.healthcarefacility.core.usecase.FindHealthcareFacilityUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FindHealthcareFacilityUseCaseUnitTest {

    @InjectMocks
    private FindHealthcareFacilityUseCase useCase;

    @Mock
    private HealthcareFacilityGateway gateway;

    @Test
    void shouldFindHealthcareFacilitySuccessFully() {
        int page = 0;
        int pageSize = 10;
        HealthcareFacility healthcareFacility = new HealthcareFacility();
        PageImpl<HealthcareFacility> pageImpl = new PageImpl<>(List.of(healthcareFacility));

        Mockito.when(gateway.findByParams(page, pageSize)).thenReturn(pageImpl);

        Page<HealthcareFacility> healthcareFacilities = Assertions.assertDoesNotThrow(() -> useCase.findByParams(page, pageSize));

        Mockito.verify(gateway).findByParams(page, pageSize);
        Assertions.assertInstanceOf(Page.class, healthcareFacilities);


    }
}
