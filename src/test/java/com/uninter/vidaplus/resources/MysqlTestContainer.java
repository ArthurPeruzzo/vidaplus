package com.uninter.vidaplus.resources;

import org.testcontainers.containers.MySQLContainer;

public class MysqlTestContainer extends MySQLContainer<MysqlTestContainer> {

    private static final String IMAGE_VERSION = "mysql:8.4.6";

    public MysqlTestContainer() {
        super(IMAGE_VERSION);
        super.withDatabaseName("vida_plus");
        super.withUsername("root");
        super.withPassword("root");
    }
}
