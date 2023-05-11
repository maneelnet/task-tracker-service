package ewp.tasktracker.entity.project;

import ewp.tasktracker.entity.BaseEntity;
import ewp.tasktracker.enums.Status;
import lombok.*;

import javax.persistence.Entity;

@Entity(name = "projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectEntity extends BaseEntity {
    private String name;
    private String description;
    private Status status;
    private String workloadId;
    private String authorId;
}
