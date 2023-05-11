package ewp.tasktracker.api.dto.history;

import ewp.tasktracker.entity.history.HistoryEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHistoryRq {
    @NotBlank(message = "Name must be not null or empty")
    @Size(max = 128)
    private String name;
    @NotBlank(message = "Description must be not null or empty")
    @Size(max = 256)
    private String description;
    @NotNull(message = "Status must be not null")
    private ProgressStatus status;
    @NotNull(message = "Priority must be not null")
    private Priority priority;
    @NotBlank(message = "EpicId must be not null or empty")
    @Size(max = 36)
    private String epicId;
    @NotBlank(message = "AuthorId must be not null or empty")
    @Size(max = 36)
    private String authorId;
    @NotBlank(message = "SprintId must be not null or empty")
    @Size(max = 36)
    private String sprintId;

    public HistoryEntity toEntity() {
        return new HistoryEntity(
                this.name,
                this.description,
                this.status,
                this.priority,
                this.epicId,
                this.authorId,
                this.sprintId
        );
    }
}
