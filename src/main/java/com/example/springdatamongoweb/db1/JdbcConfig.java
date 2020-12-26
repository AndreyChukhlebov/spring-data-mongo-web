package com.example.springdatamongoweb.db1;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(
        jdbcOperationsRef = "jdbcOperationsDataBase1",
        basePackages = {
                "com.example.springdatamongoweb.repository"
        }
)
@EnableMongoRepositories(
        basePackages = {
                "com.example.springdatamongoweb.mobgorepository"
        })
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Bean
    @Qualifier("database1")
    @ConfigurationProperties(prefix = "database1.global.hikari")
    public DataSource dataSourceGlobal(@Qualifier("database1") final DataSourceProperties propertiesDataBase1,
                                       final ObjectProvider<MBeanExporter> mBeanExporter
    ) {
        final HikariDataSource dataSource = createHikariDataSource(propertiesDataBase1);
        /* См. DataSourceJmxConfiguration.Hikari */
        mBeanExporter.ifUnique(exporter -> exporter.addExcludedBean("dataSourceGlobal"));
        return dataSource;
    }

    @Bean
    @Qualifier("database1")
    @ConfigurationProperties(prefix = "database1.global")
    public DataSourceProperties propertiesDataBase1() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("database1")
    public NamedParameterJdbcOperations jdbcOperationsDataBase1(@Qualifier("database1") DataSource dataSourceGlobal) {
        return new NamedParameterJdbcTemplate(dataSourceGlobal);
    }

    @Bean
    public Dialect jdbcDialect(@Qualifier("database1") NamedParameterJdbcOperations operations) {
        return DialectResolver.getDialect(operations.getJdbcOperations());
    }

    /**
     * according to {@link org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration}
     */
    static HikariDataSource createHikariDataSource(@Qualifier("database1") final DataSourceProperties propertiesDataBase1) {
        final HikariDataSource dataSource = propertiesDataBase1
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        if (StringUtils.hasText(propertiesDataBase1.getName())) {
            dataSource.setPoolName(propertiesDataBase1.getName());
        }
        return dataSource;
    }
}
