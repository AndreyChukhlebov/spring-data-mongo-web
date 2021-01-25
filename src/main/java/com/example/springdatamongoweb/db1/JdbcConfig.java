package com.example.springdatamongoweb.db1;

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
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
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
        jdbcOperationsRef = "jdbcOperationsDataBase1",
        basePackages = {
                "com.example.springdatamongoweb.repository"
        }
)
@EnableMongoRepositories(
        basePackages = {
                "com.example.springdatamongoweb.mobgorepository"
        })
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        JdbcRepositoriesAutoConfiguration.class
})
public class JdbcConfig extends AbstractConfig {

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
    @Qualifier("database1")
    public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext,
                                       @Qualifier("database1") NamedParameterJdbcOperations jdbcOperationsDataBase1,
                                       @Lazy RelationResolver relationResolver,
                                       JdbcCustomConversions conversions,
                                       Dialect dialect) {

        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(jdbcOperationsDataBase1.getJdbcOperations());
        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Bean
    @Qualifier("database1")
    public PlatformTransactionManager transactionManager(@Qualifier("database1") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
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
