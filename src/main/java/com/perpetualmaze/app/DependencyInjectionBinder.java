package com.perpetualmaze.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class DependencyInjectionBinder extends AbstractBinder {
    private static final String CONFIG_FILE = "application.properties";

    @Override
    protected void configure() {
        Properties props = getProperties();
        bind(props).to(Properties.class);

        DataSource dataSource = getDataSource(props);
        bind(dataSource).to(DataSource.class);

        /*
        GameStore gameStore = new PostgresGameStore(dataSource);
        bind(gameStore).to(GameStore.class);

        HighScoreStore highScoreStore = new PostgresHighScoreStore(dataSource);
        bind(highScoreStore).to(HighScoreStore.class);

        PieceStore pieceStore = new PostgresPieceStore(dataSource);
        bind(pieceStore).to(PieceStore.class);
         */
    }

    private Properties getProperties() {
        ClassLoader classLoader = DependencyInjectionBinder.class.getClassLoader();
        return ofNullable(classLoader.getResourceAsStream(CONFIG_FILE)).map(inputStream -> {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException ioException) {
                throw new RuntimeException("Failed to load properties", ioException);
            }
            return properties;
        }).orElseThrow(() -> new RuntimeException("Unable to find application.properties classpath resource"));
    }

    private DataSource getDataSource(Properties props) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(props.getProperty("db.url"));
        config.setUsername(props.getProperty("db.user"));
        config.setPassword(props.getProperty("db.pass"));
        config.setMinimumIdle(parseInt(props.getProperty("db.min.idle")));
        config.setMaximumPoolSize(parseInt(props.getProperty("db.max.pool.size")));
        config.setIdleTimeout(parseInt(props.getProperty("db.idle.timeout")));
        config.setConnectionTimeout(parseInt(props.getProperty("db.connection.timeout")));
        config.setAutoCommit(true);

        HikariDataSource dataSource = new HikariDataSource(config);
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
