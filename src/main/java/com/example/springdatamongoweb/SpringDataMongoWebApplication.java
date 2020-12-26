package com.example.springdatamongoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@SpringBootApplication
public class SpringDataMongoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMongoWebApplication.class, args);
	}

}
