package ewp.tasktracker.api.dto.task;

import ewp.tasktracker.entity.task.TaskEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateTaskRq {

    @NotNull
    private String id;

    @NotNull
    @Size(max = 128)
    private String name;
    @NotNull
    @Size(max = 256)
    private String description;
    private ProgressStatus status;
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

    public TaskEntity toEntity(){
        return new TaskEntity(
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
