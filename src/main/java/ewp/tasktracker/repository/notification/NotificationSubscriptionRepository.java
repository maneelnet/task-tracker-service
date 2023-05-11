package ewp.tasktracker.repository.notification;

import ewp.tasktracker.entity.notification.NotificationSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationSubscriptionRepository extends JpaRepository<NotificationSubscriptionEntity, String> {

    Optional<NotificationSubscriptionEntity> findByUserIdAndProjectId(String userId, String projectId);
}
