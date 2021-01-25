package com.example.springdatamongoweb.db2;

import com.example.springdatamongoweb.AbstractConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.BasicJdbcConverter;
import org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(
        jdbcOperationsRef = "jdbcOperationsDataBase2",
        basePackages = {
                "com.example.springdatamongoweb.repository2"
        }
)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        JdbcRepositoriesAutoConfiguration.class
})

public class JdbcConfig2 extends AbstractConfig {

    @Bean
    @Qualifier("database2")
    @ConfigurationProperties(prefix = "database2.global.hikari")
    public DataSource dataSource(@Qualifier("database2") final DataSourceProperties propertiesDataBase2,
                                 final ObjectProvider<MBeanExporter> mBeanExporter
    ) {
        final HikariDataSource dataSource = createHikariDataSource(propertiesDataBase2);
        /* См. DataSourceJmxConfiguration.Hikari */
        mBeanExporter.ifUnique(exporter -> exporter.addExcludedBean("dataSource"));
        return dataSource;
    }

    @Bean
    @Qualifier("database2")
    @ConfigurationProperties(prefix = "database2.global")
    public DataSourceProperties propertiesDataBase2() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("database2")
    public NamedParameterJdbcOperations jdbcOperationsDataBase2(@Qualifier("database2") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("database2")
    public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext,
                                       @Qualifier("database2") NamedParameterJdbcOperations operations,
                                       @Lazy RelationResolver relationResolver,
                                       JdbcCustomConversions conversions,
                                       Dialect dialect) {

        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(operations.getJdbcOperations());
        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Bean
    @Qualifier("database2")
    public PlatformTransactionManager transactionManager(@Qualifier("database2") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * according to {@link org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration}
     */
    static HikariDataSource createHikariDataSource(@Qualifier("database2") final DataSourceProperties propertiesDataBase2) {
        final HikariDataSource dataSource = propertiesDataBase2
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        if (StringUtils.hasText(propertiesDataBase2.getName())) {
            dataSource.setPoolName(propertiesDataBase2.getName());
        }
        return dataSource;
    }
}
