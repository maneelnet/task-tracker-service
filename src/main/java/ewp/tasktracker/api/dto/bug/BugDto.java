package ewp.tasktracker.api.dto.bug;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.bug.BugEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugDto {
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public BugDto(BugEntity bugEntity) {
        this.id = bugEntity.getId();
        this.name = bugEntity.getName();
        this.description = bugEntity.getDescription();
        this.status = bugEntity.getStatus();
        this.priority = bugEntity.getPriority();
        this.historyId = bugEntity.getHistoryId();
        this.authorId = bugEntity.getAuthorId();
        this.assigneeId = bugEntity.getAssigneeId();
        this.createdAt = bugEntity.getCreatedAt();
        this.updatedAt = bugEntity.getUpdatedAt();
    }

}
