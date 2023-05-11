package ewp.tasktracker.api.dto.workload;

import ewp.tasktracker.entity.workload.WorkloadEntity;
import ewp.tasktracker.enums.ActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkloadDto {

    private String id;
    private String name;
    private ActivityStatus status;
    private String authorId;

    public WorkloadDto(WorkloadEntity workloadEntity) {
        this.id = workloadEntity.getId();
        this.name = workloadEntity.getName();
        this.status = workloadEntity.getStatus();
        this.authorId = workloadEntity.getAuthorId();
    }
}