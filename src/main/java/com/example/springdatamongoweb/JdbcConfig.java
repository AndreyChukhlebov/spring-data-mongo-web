package com.example.springdatamongoweb;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(
        basePackages = {
                "com.example.springdatamongoweb.repository"
        })
@EnableMongoRepositories(
        basePackages = {
                "com.example.springdatamongoweb.mobgorepository"
        })
public class JdbcConfig extends AbstractJdbcConfiguration {


    @Bean
    @Primary
    @ConfigurationProperties(prefix = "database.global.hikari")
    public DataSource dataSourceGlobal(final DataSourceProperties propertiesGamification,
                                       final ObjectProvider<MBeanExporter> mBeanExporter
    ) {
        final HikariDataSource dataSource = createHikariDataSource(propertiesGamification);
        /* См. DataSourceJmxConfiguration.Hikari */
        mBeanExporter.ifUnique(exporter -> exporter.addExcludedBean("dataSourceGlobal"));
        return dataSource;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "database.global")
    public DataSourceProperties propertiesGamification() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSourceGlobal) {
        return new NamedParameterJdbcTemplate(dataSourceGlobal);
    }

    /**
     * according to {@link org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration}
     */
    static HikariDataSource createHikariDataSource(final DataSourceProperties properties) {
        final HikariDataSource dataSource = properties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }
}
