package ewp.tasktracker.api.dto.workload;

import ewp.tasktracker.enums.ActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkloadRq {
    private String id;
    @NotNull
    @Size(min = 2, max = 128)
    private String name;
    private ActivityStatus status;
    @NotNull
    @Size(max = 36)
    private String authorId;
}