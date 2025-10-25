package com.uninter.vidaplus.persona.core.usecase.healthcareprofessional;

import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.gateway.healthcareprofessional.HealthcareProfessionalGateway;
import com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.dto.HealthcareProfessionalCreateDto;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.factory.healthcareprofessional.UserHealthcareProfessionalFactory;
import com.uninter.vidaplus.security.user.core.domain.password.PasswordHash;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateHealthcareProfessionalUseCase {

    private final UserGateway userGateway;
    private final HealthcareProfessionalGateway healthcareProfessionalGateway;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(HealthcareProfessionalCreateDto dto) {
        User user = buildUserHealthcareProfessional(dto);
        User userSaved = userGateway.create(user);

        HealthcareProfessional healthcareProfessional = new HealthcareProfessional(null, userSaved.getId(), dto.name(), dto.lastName(), dto.position(), dto.email());
        healthcareProfessionalGateway.create(healthcareProfessional);
    }

    private User buildUserHealthcareProfessional(HealthcareProfessionalCreateDto dto) {
        UserParams userParams = new UserParams(dto.email(), dto.password());
        User user = new UserHealthcareProfessionalFactory().createUser(userParams);
        user.setPassword(new PasswordHash(passwordEncoder.encode(dto.password())));

        return user;
    }
}
