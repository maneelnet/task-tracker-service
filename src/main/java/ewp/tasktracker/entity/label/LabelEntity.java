package ewp.tasktracker.entity.label;

import ewp.tasktracker.api.dto.label.LabelDto;
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
@Entity(name = "labels")
public class LabelEntity extends BaseEntity {
    private String text;
    private String authorId;
    private String taskId;

    public static boolean updateLabel (LabelEntity labelEntity, LabelDto dto) {
        labelEntity.setText(dto.getText());
        labelEntity.setAuthorId(dto.getAuthorId());
        labelEntity.setTaskId(dto.getTaskId());
        labelEntity.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}
