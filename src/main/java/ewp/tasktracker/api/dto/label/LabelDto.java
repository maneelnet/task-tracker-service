package ewp.tasktracker.api.dto.label;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.label.LabelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDto {
    @NotBlank
    @Size(min = 2, max = 36)
    private String id;
    @NotBlank
    @Size(min = 2, max = 64)
    private String text;
    @NotBlank
    @Size(min = 2, max = 36)
    private String authorId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String taskId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public LabelDto(LabelEntity labelEntity) {
        this.id = labelEntity.getId();
        this.text = labelEntity.getText();
        this.authorId = labelEntity.getAuthorId();
        this.taskId = labelEntity.getTaskId();
        this.createdAt = labelEntity.getCreatedAt();
        this.updatedAt = labelEntity.getUpdatedAt();
    }
}
