package ewp.tasktracker.api.dto.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.notification.NotificationSubscriptionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSubscriptionDto {

    @NotBlank
    @Size(min = 2, max = 36)
    private String id;
    @NotBlank
    @Size(min = 2, max = 36)
    private String userId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String projectId;
    @Size(min = 4, max = 72)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public NotificationSubscriptionDto(NotificationSubscriptionEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.projectId = entity.getProjectId();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public static NotificationSubscriptionDto from (NotificationSubscriptionEntity entity) {
        return new NotificationSubscriptionDto(entity);
    }
}
