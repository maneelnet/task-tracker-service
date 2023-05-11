package ewp.tasktracker.api.dto.project;

import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private String id;
    private String name;
    private String description;
    private Status status;
    private String workloadId;
    private String authorId;

    public ProjectDto(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
        this.status = projectEntity.getStatus();
        this.workloadId = projectEntity.getWorkloadId();
        this.authorId = projectEntity.getAuthorId();
    }
}
