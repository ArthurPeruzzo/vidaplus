package com.uninter.vidaplus.resources;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Testcontainers
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractContainer {

    @Container
    protected static JdbcDatabaseContainer<?> genericContainer = new MysqlTestContainer();

    @Autowired
    protected DataSource dataSource;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", genericContainer::getJdbcUrl);
        registry.add("spring.datasource.username", genericContainer::getUsername);
        registry.add("spring.datasource.password", genericContainer::getPassword);
    }

    @AfterEach
    protected void cleanDatabase() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            disableForeignKeyChecks(statement);

            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            List<String> tables = getAllTablesAndRemoveFlywaySchema(resultSet);
            truncateAllTables(tables, statement);

            enableForeignKeyChecks(statement);
        }
    }

    private static void disableForeignKeyChecks(Statement statement) throws SQLException {
        statement.execute("SET FOREIGN_KEY_CHECKS = 0");
    }


    private static List<String> getAllTablesAndRemoveFlywaySchema(ResultSet resultSet) throws SQLException {
        List<String> tables = new ArrayList<>();
        while (resultSet.next()) {
            String tableName = resultSet.getString(1);
            if (!"flyway_schema_history".equalsIgnoreCase(tableName)) {
                tables.add(tableName);
            }
        }
        return tables;
    }

    private static void truncateAllTables(List<String> tables, Statement stmt) {
        for (String table : tables) {
            try {
                stmt.execute("TRUNCATE TABLE " + table);
            } catch (SQLException e) {
                throw new RuntimeException("erro ao truncar tabela " + table);
            }
        }
    }

    private static void enableForeignKeyChecks(Statement statement) throws SQLException {
        statement.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
