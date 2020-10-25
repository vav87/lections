package ru.digitalhabbits.sbt.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.slf4j.LoggerFactory.getLogger;

@Profile("db-test")
@TestConfiguration
public class TestDatabaseConfiguration {
    private static final Logger logger = getLogger(TestDatabaseConfiguration.class);

    private static final String IMAGE_VERSION = "postgres:13-alpine";
    private static final String DATABASE_NAME = "simple_web";
    private static final String USERNAME = "program";
    private static final String PASSWORD = "test";

    @Bean
    public PostgreSQLContainer<?> postgres() {
        final PostgreSQLContainer<?> postgres =
                new PostgreSQLContainer<>(IMAGE_VERSION)
                        .withDatabaseName(DATABASE_NAME)
                        .withUsername(USERNAME)
                        .withPassword(PASSWORD);
        postgres.start();
        return postgres;
    }

    @Primary
    @DependsOn("postgres")
    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        logger.info("### Use connect to postgres [{}]", postgres().getJdbcUrl());
        dataSource.setJdbcUrl(postgres().getJdbcUrl());
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setDriverClassName(Driver.class.getCanonicalName());
        return dataSource;
    }
}
