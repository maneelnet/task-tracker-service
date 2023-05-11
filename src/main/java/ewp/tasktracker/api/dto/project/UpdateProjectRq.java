package ewp.tasktracker.api.dto.project;

import com.sun.istack.NotNull;
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
public class UpdateProjectRq {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String description;
    private Status status;
}
