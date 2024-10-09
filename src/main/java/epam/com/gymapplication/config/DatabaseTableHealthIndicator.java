package epam.com.gymapplication.config;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component
public class DatabaseTableHealthIndicator implements HealthIndicator {
    private static final Long MB_THRESHOLD = 1L;

    private final MeterRegistry meterRegistry;
    private final JdbcTemplate jdbcTemplate;
    private Counter healthCheckCounter;

    public DatabaseTableHealthIndicator(MeterRegistry meterRegistry, JdbcTemplate jdbcTemplate) {
        this.meterRegistry = meterRegistry;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        healthCheckCounter = meterRegistry.counter("db_health_check_counter", "type", "database");

    }


    @Override
    public Health health() {
        healthCheckCounter.increment();

        String query = "SELECT round((pg_total_relation_size('training') + " +
                "pg_indexes_size('training') + " +
                "pg_table_size('training')) / 1024 / 1024) AS size_mb";

        Long tableSizeInMB = jdbcTemplate.queryForObject(query, Long.class);


        if (tableSizeInMB != null && tableSizeInMB < MB_THRESHOLD) {
            return Health.up()
                    .withDetail("tableSizeMB", tableSizeInMB)
                    .build();
        } else {
            return Health.down()
                    .withDetail("error", "Table size exceeds threshold")
                    .withDetail("tableSizeMB", tableSizeInMB)
                    .build();
        }
    }
}