package com.uninter.vidaplus.persona.core.usecase.administrator;

import com.uninter.vidaplus.persona.core.domain.administrator.Administrator;
import com.uninter.vidaplus.persona.core.gateway.administrator.AdministratorGateway;
import com.uninter.vidaplus.persona.infra.controller.administrator.dto.AdministratorCreateDto;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.factory.UserParams;
import com.uninter.vidaplus.security.user.core.domain.factory.administrator.UserAdministratorFactory;
import com.uninter.vidaplus.security.user.core.domain.password.PasswordHash;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdministratorUseCase {

    private final UserGateway userGateway;
    private final AdministratorGateway administratorGateway;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(AdministratorCreateDto dto) {

        User user = buildUserAdministrator(dto);

        User userSaved = userGateway.create(user);

        Administrator administrator = new Administrator(null, userSaved.getId(), dto.name(), dto.lastName(), dto.email());

        administratorGateway.create(administrator);
    }

    private User buildUserAdministrator(AdministratorCreateDto dto) {
        UserParams userParams = new UserParams(dto.email(), dto.password());
        User user = new UserAdministratorFactory().createUser(userParams);
        user.setPassword(new PasswordHash(passwordEncoder.encode(dto.password())));

        return user;
    }
}
