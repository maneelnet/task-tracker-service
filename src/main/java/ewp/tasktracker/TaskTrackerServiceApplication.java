package ewp.tasktracker;

import ewp.tasktracker.service.kafka.Notification;
import ewp.tasktracker.service.kafka.NotificationKafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@SpringBootApplication
@EnableWebMvc
public class TaskTrackerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerServiceApplication.class, args);
    }

}
