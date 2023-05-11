package ewp.tasktracker.api.dto.label;

import ewp.tasktracker.entity.label.LabelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLabelRq {
    @NotBlank
    @Size(min = 2, max = 64)
    private String text;
    @NotBlank
    @Size(min = 2, max = 36)
    private String authorId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String taskId;

    public LabelEntity toEntity() {
        return new LabelEntity(
                this.text,
                this.authorId,
                this.taskId
        );
    }
}
