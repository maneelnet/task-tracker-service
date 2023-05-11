package ewp.tasktracker.entity.epic;

import ewp.tasktracker.api.dto.epic.EpicDto;
import ewp.tasktracker.entity.BaseEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "epics")
public class EpicEntity extends BaseEntity {

    private String name;
    private String description;
    private ProgressStatus status;
    private Priority priority;
    private String projectId;
    private String authorId;
    private String supersprintId;


    public static boolean updateEpic (EpicEntity epicEntity, EpicDto dto) {
        epicEntity.setName(dto.getName());
        epicEntity.setDescription(dto.getDescription());
        epicEntity.setStatus(dto.getStatus());
        epicEntity.setPriority(dto.getPriority());
        epicEntity.setProjectId(dto.getProjectId());
        epicEntity.setAuthorId(dto.getAuthorId());
        epicEntity.setSupersprintId(dto.getSupersprintId());
        epicEntity.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}
