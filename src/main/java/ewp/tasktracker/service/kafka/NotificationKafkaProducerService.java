package ewp.tasktracker.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationKafkaProducerService {
    private final KafkaTemplate<String, Notification> kafkaTemplate;
    @Value("${spring.kafka.user-notifications.topic}")
    private String topic;

    public void send(Notification notification) {
        kafkaTemplate.send(topic, notification);
    }
}
