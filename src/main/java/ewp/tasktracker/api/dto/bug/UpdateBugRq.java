package ewp.tasktracker.api.dto.bug;


import ewp.tasktracker.entity.bug.BugEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBugRq {

    @NotNull
    private String id;
    @NotNull
    @Size(min = 2, max = 128)
    private String name;
    @NotNull
    @Size(max = 256)
    private String description;
    @NotNull
    private ProgressStatus status;
    @NotNull
    private Priority priority;
    @NotNull
    @Size(max = 36)
    private String historyId;
    @NotNull
    @Size(max = 36)
    private String authorId;
    @NotNull
    @Size(max = 36)
    private String assigneeId;

    public BugEntity toEntity() {
        return new BugEntity(
                this.name,
                this.description,
                this.status,
                this.priority,
                this.historyId,
                this.authorId,
                this.assigneeId
        );
    }
}
