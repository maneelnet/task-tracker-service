package ewp.tasktracker.entity.bug;

import ewp.tasktracker.entity.BaseEntity;
import ewp.tasktracker.api.dto.bug.UpdateBugRq;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bugs")
public class BugEntity extends BaseEntity {
    private String name;
    private String description;
    private ProgressStatus status;
    private Priority priority;
    private String historyId;
    private String authorId;
    private String assigneeId;

    public void updateBug(UpdateBugRq updateBugRq) {
        this.setName(updateBugRq.getName());
        this.setDescription(updateBugRq.getDescription());
        this.setStatus(updateBugRq.getStatus());
        this.setPriority(updateBugRq.getPriority());
        this.setHistoryId(updateBugRq.getHistoryId());
        this.setAuthorId(updateBugRq.getAuthorId());
        this.setAssigneeId(updateBugRq.getAssigneeId());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
