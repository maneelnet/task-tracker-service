package ewp.tasktracker.api.dto.epic;

import ewp.tasktracker.entity.epic.EpicEntity;
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
public class CreateEpicRq {
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotBlank
    @Size(min = 2, max = 256)
    private String description;
    @NotNull
    private ProgressStatus status;
    @NotNull
    private Priority priority;
    @NotBlank
    @Size(min = 2, max = 36)
    private String projectId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String authorId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String supersprintId;

    public EpicEntity toEntity() {
        return new EpicEntity(
                this.name,
                this.description,
                this.status,
                this.priority,
                this.projectId,
                this.authorId,
                this.supersprintId
        );

    }
}
