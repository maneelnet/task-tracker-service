package ewp.tasktracker.entity.notification;

import ewp.tasktracker.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "notification_subscriptions")
public class NotificationSubscriptionEntity extends BaseEntity {

    private String userId;
    private String projectId;
}
