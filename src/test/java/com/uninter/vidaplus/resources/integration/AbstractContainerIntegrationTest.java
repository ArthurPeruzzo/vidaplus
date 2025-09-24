package com.uninter.vidaplus.resources.integration;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.ContainerConfig;
import com.uninter.vidaplus.resources.AbstractContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.ContainerState;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ActiveProfiles("integration-test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbstractContainerIntegrationTest extends AbstractContainer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldCreateContainerSuccessFully() {
        boolean isCreated = genericContainer.isCreated();
        String containerId = genericContainer.getContainerId();
        String jdbcUrl = genericContainer.getJdbcUrl();
        String username = genericContainer.getUsername();
        String password = genericContainer.getPassword();
        String databaseName = genericContainer.getDatabaseName();
        String image = Optional.of(genericContainer)
                .map(ContainerState::getCurrentContainerInfo)
                .map(InspectContainerResponse::getConfig)
                .map(ContainerConfig::getImage).orElse(null);

        Assertions.assertTrue(isCreated);
        Assertions.assertNotNull(containerId);
        Assertions.assertNotNull(jdbcUrl);
        Assertions.assertEquals("root", username);
        Assertions.assertEquals("root", password);
        Assertions.assertEquals("vida_plus", databaseName);
        Assertions.assertNotNull(image);
        Assertions.assertEquals("mysql:8.4.6", image);
    }

    //TODO isso so funciona em certos contextos.
    @ParameterizedTest
    @ValueSource(strings = {"Test 1", "Test 2", "Test 3"})
    void shouldHaveOneElementInTable_TransactionalOptional(String value) {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50))");
        jdbcTemplate.update("INSERT INTO test_table (name) VALUES (?)", value);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM test_table");
        int size = rows.size();
        Assertions.assertEquals(1, size, "Deve haver somente 1 registro na tabela");
    }

}
