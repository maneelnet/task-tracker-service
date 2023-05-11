package ewp.tasktracker.api.dto.epic;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.epic.EpicEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpicDto {
    private String id;
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotBlank
    @Size(min = 2, max = 256)
    private String description;
    @NotNull
    private ProgressStatus status;
    @NotNull
    private Priority priority;
    @NotBlank
    @Size(min = 2, max = 36)
    private String projectId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String authorId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String supersprintId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public EpicDto(EpicEntity epicEntity) {
        this.id = epicEntity.getId();
        this.name = epicEntity.getName();
        this.description = epicEntity.getDescription();
        this.status = epicEntity.getStatus();
        this.priority = epicEntity.getPriority();
        this.projectId = epicEntity.getProjectId();
        this.authorId = epicEntity.getAuthorId();
        this.supersprintId = epicEntity.getSupersprintId();
        this.createdAt = epicEntity.getCreatedAt();
        this.updatedAt = epicEntity.getUpdatedAt();
    }

    public EpicEntity toEntity() {
        return new EpicEntity(
                this.name,
                this.description,
                this.status,
                this.priority,
                this.projectId,
                this.authorId,
                this.supersprintId
        );
    }
    public static EpicDto from (EpicEntity epicEntity) {
        return new EpicDto(epicEntity);
    }

}
