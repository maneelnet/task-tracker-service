package ewp.tasktracker.entity.history;

import ewp.tasktracker.api.dto.history.UpdateHistoryRq;
import ewp.tasktracker.entity.BaseEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "histories")
public class HistoryEntity extends BaseEntity {

    private String name;
    private String description;
    private ProgressStatus status;
    private Priority priority;
    private String epicId;
    private String authorId;
    private String sprintId;

    public HistoryEntity(String name, String description, ProgressStatus status, Priority priority) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    public  void from(UpdateHistoryRq dto) {
                this.name = dto.getName();
                this.description = dto.getDescription();
                this.status = dto.getStatus();
                this.priority = dto.getPriority();
                this.updatedAt = LocalDateTime.now();
    }
}
