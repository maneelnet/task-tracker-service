package ewp.tasktracker.api.dto.sprint;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.sprint.SprintEntity;
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
public class CreateSprintRq {
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime startAt;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    protected LocalDateTime endAt;
    @NotBlank
    @Size(min = 2, max = 36)
    private String authorId;
    @NotBlank
    @Size(min = 2, max = 36)
    private String supersprintId;

    public SprintEntity toEntity() {
        return new SprintEntity(
                this.name,
                this.startAt,
                this.endAt,
                this.authorId,
                this.supersprintId
        );

    }
}
