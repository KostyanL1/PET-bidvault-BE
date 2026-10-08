package com.legenkiy;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;
import java.util.TimeZone;

public class TestContainerResource implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgres;

    @Override
    @SuppressWarnings("resource")
    public Map<String, String> start() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        postgres = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("bidvault_test")
                .withUsername("test")
                .withPassword("test");

        postgres.start();

        return Map.of(
                "quarkus.datasource.jdbc.url", postgres.getJdbcUrl(),
                "quarkus.datasource.username", postgres.getUsername(),
                "quarkus.datasource.password", postgres.getPassword()
        );
    }

    @Override
    public void stop() {
        if (postgres != null) {
            postgres.stop();
        }
    }
}
