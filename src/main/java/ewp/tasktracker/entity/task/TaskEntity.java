package ewp.tasktracker.entity.task;

import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.entity.BaseEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;


@Entity(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity extends BaseEntity {

    private String name;

    private String description;

    private ProgressStatus status;

    private Priority priority;

    @Column(name="history_id")
    private String historyId;

    @Column(name="author_id")
    private String authorId;

    @Column(name="assignee_id")
    private String assigneeId;

    public void updateTask(TaskDto taskDto){
        this.setName(taskDto.getName());
        this.setDescription(taskDto.getDescription());
        this.setStatus(taskDto.getStatus());
        this.setPriority(taskDto.getPriority());
        this.setHistoryId(taskDto.getHistoryId());
        this.setAuthorId(taskDto.getAuthorId());
        this.setAssigneeId(taskDto.getAssigneeId());
    }


}

