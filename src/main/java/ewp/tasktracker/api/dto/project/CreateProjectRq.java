package ewp.tasktracker.api.dto.project;

import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProjectRq {
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotBlank
    @Size(max = 255)
    private String description;
    private Status status;
    @NotBlank
    @Size(max = 36)
    private String workloadId;
    @NotBlank
    @Size(max = 36)
    private String author_id;

    public ProjectEntity toEntity() {
        return new ProjectEntity(
                this.name,
                this.description,
                this.status,
                this.workloadId,
                this.author_id
        );
    }
}
