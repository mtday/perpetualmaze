package com.perpetualmaze.store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;

public class ExternalDataSourceResource extends ExternalResource {
    protected HikariDataSource dataSource;

    @Override
    protected void before() throws Throwable {
        super.before();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:hsqldb:mem:db");
        config.setUsername("SA");
        config.setPassword("");
        config.setAutoCommit(true);
        dataSource = new HikariDataSource(config);

        Flyway.configure().dataSource(dataSource).load().migrate();
    }

    @Override
    protected void after() {
        super.after();

        if (dataSource != null) {
            dataSource.close();
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
