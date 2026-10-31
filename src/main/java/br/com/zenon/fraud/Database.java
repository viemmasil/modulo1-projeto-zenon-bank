package br.com.zenon.fraud;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.time.Duration;

public final class Database {

    private static final HikariDataSource DATA_SOURCE = createDataSource();

    private Database() { }

    private static HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3306/zenon_bank?rewriteBatchedStatements=true");
        config.setUsername("root");
        config.setPassword("rootpassword123");
        config.setPoolName("zenon-bank-mysql-pool");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(Duration.ofSeconds(10).toMillis());
        config.setIdleTimeout(Duration.ofMinutes(5).toMillis());
        config.setMaxLifetime(Duration.ofMinutes(30).toMillis());
        config.setKeepaliveTime(Duration.ofSeconds(30).toMillis());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        return new HikariDataSource(config);
    }

    public static DataSource dataSource() {
        return DATA_SOURCE;
    }

    public static void shutdown() {
        DATA_SOURCE.close();
    }
}
