package com.uninter.vidaplus.persona.integration;

import com.uninter.vidaplus.resources.testcontainer.AbstractContainer;
import com.uninter.vidaplus.security.infra.config.SecurityConfiguration;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.infra.entity.RoleEntity;
import com.uninter.vidaplus.security.user.infra.entity.UserEntity;
import com.uninter.vidaplus.security.user.infra.repository.RoleRepository;
import com.uninter.vidaplus.security.user.infra.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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
class HealthcareProfessionalIntegrationTest extends AbstractContainer {
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
    void shouldCreateSuccessFully() {

        RoleEntity roleEntity = new RoleEntity(null, RoleEnum.ROLE_ADMINISTRATOR);

        roleRepository.saveAndFlush(roleEntity);

        UserEntity user = UserEntity.builder()
                .email("any@any.com")
                .password(securityConfiguration.passwordEncoder().encode("any"))
                .roles(List.of(roleEntity))
                .build();

        userRepository.saveAndFlush(user);

        String token = RestAssured
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
                .body("token", Matchers.instanceOf(String.class))
                .extract()
                .path("token");

        Assertions.assertNotNull(token);
        Header header = new Header("Authorization", token);

        RestAssured
                .given()
                .contentType("application/json")
                .header(header)
                .body("""
                        {
                            "name": "Teste",
                            "cnpj": "99607784000188"
                        }
                        """)
                .when()
                .post("/healthcare-facilities")
                .then()
                .statusCode(201);


        RestAssured
                .given()
                .contentType("application/json")
                .header(header)
                .body("""
                        {
                            "name": "Teste",
                            "lastName": "testee",
                            "email": "any1@any.com",
                            "position": "MEDIC",
                            "healthcareFacilityId": 1,
                            "password": "anyAaaaad#123"
                        }
                        """)
                .when()
                .post("/healthcare-professionals")
                .then()
                .statusCode(201);

    }
}
