package ewp.tasktracker.entity.sprint;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.api.dto.sprint.SprintDto;
import ewp.tasktracker.entity.BaseEntity;
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
@Entity(name = "sprints")
public class SprintEntity extends BaseEntity {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime startAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime endAt = LocalDateTime.now();
    private String authorId;
    private String supersprintId;

    public static boolean updateSprint(SprintEntity sprintEntity, SprintDto dto) {
        sprintEntity.setName(dto.getName());
        sprintEntity.setStartAt(dto.getStartAt());
        sprintEntity.setEndAt(dto.getEndAt());
        sprintEntity.setAuthorId(dto.getAuthorId());
        sprintEntity.setSupersprintId(dto.getSupersprintId());
        sprintEntity.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}