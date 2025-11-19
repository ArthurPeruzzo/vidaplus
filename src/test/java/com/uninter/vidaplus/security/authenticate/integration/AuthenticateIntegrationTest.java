package com.uninter.vidaplus.security.authenticate.integration;

import com.uninter.vidaplus.resources.testcontainer.AbstractContainer;
import com.uninter.vidaplus.security.infra.config.SecurityConfiguration;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import com.uninter.vidaplus.security.user.infra.repository.RoleRepository;
import com.uninter.vidaplus.security.user.infra.repository.UserRepository;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticateIntegrationTest extends AbstractContainer {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void shouldAuthenticateSuccessFully() {

        RoleEntity roleEntity = new RoleEntity(null, RoleEnum.ROLE_ADMINISTRATOR);

        roleRepository.saveAndFlush(roleEntity);

        UserEntity user = UserEntity.builder()
                .email("any@any.com")
                .password(securityConfiguration.passwordEncoder().encode("any"))
                .roles(List.of(roleEntity))
                .build();

        userRepository.saveAndFlush(user);

        RestAssured
                .given()
                    .contentType("application/json")
                    .body("""
                            {
                                "email": "any@any.com",
                                "password": "any"
                            }
                            """)
                .when()
                    .post("/authenticate/login")
                .then()
                    .statusCode(200)
                .body("token", Matchers.notNullValue())
                .body("token", Matchers.instanceOf(String.class));
    }
}
