package ewp.tasktracker.api.dto.notification;

import ewp.tasktracker.entity.notification.NotificationSubscriptionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNotificationSubscriptionRq {

    private String userId;
    private String projectId;

    public NotificationSubscriptionEntity toEntity() {
        return new NotificationSubscriptionEntity(
                this.userId,
                this.projectId
        );

    }
}
