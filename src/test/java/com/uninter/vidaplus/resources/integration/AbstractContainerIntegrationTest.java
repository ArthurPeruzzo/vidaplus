package com.uninter.vidaplus.resources.integration;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.ContainerConfig;
import com.uninter.vidaplus.resources.AbstractContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ContainerState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

class AbstractContainerIntegrationTest extends AbstractContainer {

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

    @Test
    void shouldCleanDatabaseAfterEach() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(50))");
            stmt.execute("INSERT INTO test_table (id, name) VALUES (1, 'Test')");
        }

        cleanDatabase();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table")) {
                rs.next();
                int count = rs.getInt(1);
                Assertions.assertEquals(0, count, "Tabela deve estar vazia após o cleanup");
            }

            try (ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM flyway_schema_history")) {
                rs1.next();
                int flywayCount = rs1.getInt(1);
                Assertions.assertTrue(flywayCount > 0, "Tabela do Flyway não deve estar vazia");
            }
        }
    }

}
