package org.example.first.config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {
    private static final HikariDataSource dataSource;

    // 静态代码块初始化连接池
    static {
        /*HikariConfig config = new HikariConfig();

        // 基本配置
        config.setJdbcUrl("jdbc:mysql://localhost:3306/world?serverTimezone=UTC&useSSL=false&characterEncoding=utf8");
        config.setUsername("root");
        config.setPassword("tree2025");

        // 连接池优化配置
        config.setMaximumPoolSize(20);          // 最大连接数
        config.setMinimumIdle(5);               // 最小空闲连接数
        config.setIdleTimeout(30000);           // 空闲连接超时时间（毫秒）
        config.setConnectionTimeout(30000);     // 连接超时时间（毫秒）
        config.setMaxLifetime(1800000);         // 连接最大生命周期（毫秒）

        // MySQL 特定配置
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);*/
        try (InputStream input = HikariCPDataSource.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            System.out.println(input);
            prop.load(input);
            System.out.println(prop);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("jdbc.url"));
            config.setUsername(prop.getProperty("jdbc.username"));
            config.setPassword(prop.getProperty("jdbc.password"));

            config.setMaximumPoolSize(Integer.parseInt(prop.getProperty("pool.maximumPoolSize")));
            config.setMinimumIdle(Integer.parseInt(prop.getProperty("pool.minimumIdle")));
            config.setIdleTimeout(Long.parseLong(prop.getProperty("pool.idleTimeout")));
            config.setConnectionTimeout(Long.parseLong(prop.getProperty("pool.connectionTimeout")));
            config.setMaxLifetime(Long.parseLong(prop.getProperty("pool.maxLifetime")));

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load database configuration: " + e.getMessage());
        }
    }

    // 获取数据源
    public static DataSource getDataSource() {
        return dataSource;
    }

    // 获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 关闭连接池
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
