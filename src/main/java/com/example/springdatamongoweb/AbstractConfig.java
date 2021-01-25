package com.example.springdatamongoweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.PostgresDialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;

import java.util.Optional;

/**
 * Данную конфигурацию необходимо использовать для корректной работы приложений с несколькими бд вместо
 * ({@link org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration})
 * Так же нужно будет отключить автоконфигурацию
 * ({@link org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration})
 * ({@link org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration})
 *  Данное решение не является целевым, необходимо перевести на ({@link org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration})
 *  после решения проблемы https://github.com/spring-projects/spring-data-jdbc/issues/544
 */
@Configuration(proxyBeanMethods = false)
public class AbstractConfig {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions();
    }

    @Bean
    public Dialect jdbcDialect() {
        return PostgresDialect.INSTANCE;
    }

    @Bean
    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy, JdbcCustomConversions customConversions) {
        JdbcMappingContext mappingContext = new JdbcMappingContext((NamingStrategy)namingStrategy.orElse(NamingStrategy.INSTANCE));
        mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
        return mappingContext;
    }
}
