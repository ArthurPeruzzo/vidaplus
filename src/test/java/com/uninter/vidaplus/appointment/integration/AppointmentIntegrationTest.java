package com.uninter.vidaplus.appointment.integration;

import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.infra.gateway.entity.AppointmentEntity;
import com.uninter.vidaplus.appointment.infra.gateway.repository.AppointmentRepository;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.reposioty.HealthcareFacilityRepository;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppointmentIntegrationTest extends AbstractContainer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private HealthcareFacilityRepository healthcareFacilityRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void shouldCreateAndCancelAppointmentSuccessFully() {
        List<RoleEntity> roles = roleRepository.findByNameIn(List.of(RoleEnum.ROLE_ADMINISTRATOR));

        UserEntity user = UserEntity.builder()
                .email("any22@email.com")
                .password(securityConfiguration.passwordEncoder().encode("VidaPlus2025!@#"))
                .roles(roles)
                .build();

        userRepository.saveAndFlush(user);

        //Login admin
        String token = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                            "email": "any22@email.com",
                            "password": "VidaPlus2025!@#"
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

        //Criacao unidade hospitlaar
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

        Long healthcareFacilityId = healthcareFacilityRepository.findAll().getFirst().getId();

        String json = String.format("""
            {
                "name": "Teste",
                "lastName": "testee",
                "email": "any23@email.com",
                "position": "MEDIC",
                "healthcareFacilityId": %d,
                "password": "VidaPlus2025!@#"
            }
            """, healthcareFacilityId);


        //Criacao Profissional de saude
        RestAssured
                .given()
                .contentType("application/json")
                .header(header)
                .body(json)
                .when()
                .post("/healthcare-professionals")
                .then()
                .statusCode(201);

        //Login do profissional de saude criado
        String tokenHealthCareProfessional = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                            "email": "any23@email.com",
                            "password": "VidaPlus2025!@#"
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

        Header headerHealthcareProfessional = new Header("Authorization", tokenHealthCareProfessional);

        //Criacao dos horarios
        RestAssured
                .given()
                .contentType("application/json")
                .header(headerHealthcareProfessional)
                .when()
                .post("/time-slots")
                .then()
                .statusCode(201);

        //Criacao do paciente

        String jsonPatient = String.format("""
            {
                "name": "Teste",
                "lastName": "testee",
                "email": "anya22@any.com",
                "sex": "MALE",
                "healthcareFacilityId": %d,
                "password": "VidaPlus2025!@#"
            }
            """, healthcareFacilityId);

        RestAssured
                .given()
                .contentType("application/json")
                .header(header)
                .body(jsonPatient)
                .when()
                .post("/patients")
                .then()
                .statusCode(201);

        //Login paciente
        String tokenPatient = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                            "email": "anya22@any.com",
                            "password": "VidaPlus2025!@#"
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

        Header headerPatient = new Header("Authorization", tokenPatient);

        String jsonCreateAppointment = String.format("""
                {
                  "date": "%s",
                  "healthcareProfessionalId": 1,
                  "type": "IN_PERSON"
                }
            """, nextValidAppointmentDate());

        //Criacao consulta
        RestAssured
                .given()
                .contentType("application/json")
                .header(headerPatient)
                .body(jsonCreateAppointment)
                .when()
                .post("/appointments")
                .then()
                .statusCode(201);

        List<AppointmentEntity> appointments = appointmentRepository.findAll();
        Assertions.assertNotNull(appointments);

        Long appointmentId = appointments.getFirst().getId();
        String cancelJson = String.format("{\"appointmentId\":%d}", appointmentId);

        //cancelar consulta
        RestAssured
                .given()
                .contentType("application/json")
                .header(headerPatient)
                .body(cancelJson)
                .when()
                .post("/appointments/cancel")
                .then()
                .statusCode(200);

        AppointmentEntity appointment = appointmentRepository.findAll().getFirst();
        Assertions.assertEquals(AppointmentStatus.CANCELED, appointment.getStatus());
    }

    private String nextValidAppointmentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDate date = LocalDate.now().plusDays(1);

        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }

        return date.atTime(13, 0).format(formatter);
    }

}
