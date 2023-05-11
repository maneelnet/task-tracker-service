package ewp.tasktracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "task-tracker")
@Data
public class TaskTrackerProperties {
    private Integer pageDefaultSize;
    private Integer pageMaxSize;
    private Duration notificationPeriod;
}