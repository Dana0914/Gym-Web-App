package epam.com.gymapplication.config;


import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@EnableAutoConfiguration
public class AppConfig {


    @Bean
    public HealthIndicator postgresTableSizeHealthIndicator(MeterRegistry meterRegistry, JdbcTemplate jdbcTemplate) {
        return new DatabaseTableHealthIndicator(meterRegistry, jdbcTemplate);
    }





}
