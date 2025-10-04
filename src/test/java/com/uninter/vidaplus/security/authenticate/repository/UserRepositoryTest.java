package com.uninter.vidaplus.security.authenticate.repository;

import com.uninter.vidaplus.resources.testcontainer.AbstractContainer;
import com.uninter.vidaplus.security.core.gateway.entity.RoleEntity;
import com.uninter.vidaplus.security.core.gateway.entity.UserEntity;
import com.uninter.vidaplus.security.core.gateway.repository.UserRepository;
import com.uninter.vidaplus.security.databuilder.entity.RoleEntityDataBuilder;
import com.uninter.vidaplus.security.databuilder.entity.UserEntityDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("integration-test")
@DataJpaTest
class UserRepositoryTest extends AbstractContainer {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSuccessFindByEmail() {
        String email = "any@email.com";
        RoleEntity roleEntity = new RoleEntityDataBuilder()
                .withId(null)
                .build();

        UserEntity userEntity = new UserEntityDataBuilder()
                .withId(null)
                .withEmail(email)
                .withRoles(List.of(roleEntity)).build();

        userRepository.save(userEntity);

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        Assertions.assertTrue(userEntityOptional.isPresent());
    }


}
