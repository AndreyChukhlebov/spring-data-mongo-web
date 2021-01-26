package com.example.springdatamongoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {
        PrometeusConfig.class
})
public class SpringDataMongoWebApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringDataMongoWebApplication.class);
        app.setApplicationStartup(new BufferingApplicationStartup(2048));
        app.run(args);
    }
}
