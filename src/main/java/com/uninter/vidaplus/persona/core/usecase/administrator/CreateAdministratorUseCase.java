package com.uninter.vidaplus.persona.core.usecase.administrator;

import com.uninter.vidaplus.persona.core.domain.administrator.Administrator;
import com.uninter.vidaplus.persona.core.gateway.administrator.AdministratorGateway;
import com.uninter.vidaplus.persona.infra.controller.administrator.dto.AdministratorCreateDto;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.factory.administrator.UserAdministratorFactory;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdministratorUseCase {

    private final UserGateway userGateway;
    private final AdministratorGateway administratorGateway;

    @Transactional
    public void create(AdministratorCreateDto dto) {
        UserParams userParams = new UserParams(dto.email(), dto.password());
        User user = new UserAdministratorFactory().createUser(userParams);
        User userSaved = userGateway.create(user);

        Administrator administrator = new Administrator(null, userSaved.getId(), dto.name(), dto.lastName(), dto.email());

        administratorGateway.create(administrator);
    }
}
