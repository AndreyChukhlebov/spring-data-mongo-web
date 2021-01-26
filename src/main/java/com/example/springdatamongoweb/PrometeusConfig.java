package com.example.springdatamongoweb;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
        MetricsAutoConfiguration.class,
        PrometheusMetricsExportAutoConfiguration.class
})
@Configuration
public class PrometeusConfig {

}
