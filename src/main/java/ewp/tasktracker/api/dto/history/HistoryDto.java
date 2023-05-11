package ewp.tasktracker.api.dto.history;

import ewp.tasktracker.entity.history.HistoryEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {

    private String id;
    private String name;
    private String description;
    private ProgressStatus status;
    private Priority priority;
    private String epicId;
    private String authorId;
    private String sprintId;
    public HistoryDto(HistoryEntity historyEntity) {
        this.id = historyEntity.getId();
        this.name = historyEntity.getName();
        this.description = historyEntity.getDescription();
        this.status = historyEntity.getStatus();
        this.priority = historyEntity.getPriority();
        this.epicId = historyEntity.getEpicId();
        this.authorId = historyEntity.getAuthorId();
        this.sprintId = historyEntity.getSprintId();
    }
}
