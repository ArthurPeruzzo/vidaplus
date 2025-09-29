package com.uninter.vidaplus.resources.testcontainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;

public abstract class AbstractContainer {

    protected static JdbcDatabaseContainer<?> genericContainer = new MysqlTestContainer();

    static {
        genericContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", genericContainer::getJdbcUrl);
        registry.add("spring.datasource.username", genericContainer::getUsername);
        registry.add("spring.datasource.password", genericContainer::getPassword);
    }
}
