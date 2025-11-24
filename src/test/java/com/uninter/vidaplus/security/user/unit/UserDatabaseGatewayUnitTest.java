package com.uninter.vidaplus.security.user.unit;


import com.uninter.vidaplus.infra.exception.ErrorAccessDatabaseException;
import com.uninter.vidaplus.persona.core.domain.exception.PersonaAlreadyCreatedException;
import com.uninter.vidaplus.security.user.core.domain.Email;
import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.password.Password;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import com.uninter.vidaplus.security.user.infra.gateway.database.UserDatabaseGateway;
import com.uninter.vidaplus.security.user.infra.repository.RoleRepository;
import com.uninter.vidaplus.security.user.infra.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDatabaseGatewayUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserDatabaseGateway gateway;

    @Test
    void shouldReturnUserWhenEmailExists() {
        RoleEntity roleEntity = new RoleEntity(1L, RoleEnum.ROLE_PATIENT);
        UserEntity entity = new UserEntity(10L, "email@test.com", "VidaPlus2025!@#", List.of(roleEntity));

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.of(entity));

        Optional<User> result = gateway.findByEmail("email@test.com");

        assertTrue(result.isPresent());
        User user = result.get();
        assertEquals(10L, user.getId());
        assertEquals("email@test.com", user.getEmail().value());
        assertEquals("VidaPlus2025!@#", user.getPassword().getValue());
        assertEquals(RoleEnum.ROLE_PATIENT, user.getRoles().getFirst().getName());
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        when(userRepository.findByEmail("notfound@test.com"))
                .thenReturn(Optional.empty());

        Optional<User> result = gateway.findByEmail("notfound@test.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowErrorAccessDatabaseExceptionWhenUnexpectedErrorOccursOnFind() {
        when(userRepository.findByEmail(anyString()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(ErrorAccessDatabaseException.class, () ->
                gateway.findByEmail("email@test.com")
        );
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User user = new User(
                new Email("email@test.com"),
                new Password("VidaPlus2025!@#"),
                List.of(new Role(RoleEnum.ROLE_PATIENT))
        );

        RoleEntity roleEntity = new RoleEntity(1L, RoleEnum.ROLE_PATIENT);
        UserEntity savedEntity = new UserEntity(50L, "email@test.com", "VidaPlus2025!@#", List.of(roleEntity));

        when(roleRepository.findByNameIn(List.of(RoleEnum.ROLE_PATIENT)))
                .thenReturn(List.of(roleEntity));

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedEntity);

        User created = gateway.create(user);

        assertNotNull(created.getId());
        assertEquals(50L, created.getId());
        assertEquals("email@test.com", created.getEmail().value());
        assertEquals("VidaPlus2025!@#", created.getPassword().getValue());
        assertEquals(RoleEnum.ROLE_PATIENT, created.getRoles().getFirst().getName());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowPersonaAlreadyCreatedExceptionWhenEmailIsDuplicated() {
        User user = new User(
                new Email("email@test.com"),
                new Password("VidaPlus2025!@#"),
                List.of(new Role(RoleEnum.ROLE_PATIENT))
        );

        when(roleRepository.findByNameIn(any()))
                .thenReturn(List.of(new RoleEntity(1L, RoleEnum.ROLE_PATIENT)));

        when(userRepository.save(any(UserEntity.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        assertThrows(PersonaAlreadyCreatedException.class, () ->
                gateway.create(user)
        );
    }

    @Test
    void shouldThrowErrorAccessDatabaseExceptionWhenUnexpectedErrorOccursOnCreate() {
        User user = new User(
                new Email("email@test.com"),
                new Password("VidaPlus2025!@#"),
                List.of(new Role(RoleEnum.ROLE_PATIENT))
        );

        when(roleRepository.findByNameIn(any()))
                .thenReturn(List.of(new RoleEntity(1L, RoleEnum.ROLE_PATIENT)));

        when(userRepository.save(any(UserEntity.class)))
                .thenThrow(new RuntimeException("DB failure"));

        assertThrows(ErrorAccessDatabaseException.class, () ->
                gateway.create(user)
        );
    }
}

