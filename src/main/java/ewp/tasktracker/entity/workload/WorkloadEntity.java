package ewp.tasktracker.entity.workload;

import ewp.tasktracker.api.dto.workload.UpdateWorkloadRq;
import ewp.tasktracker.entity.BaseEntity;
import ewp.tasktracker.enums.ActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "workloads")
public class WorkloadEntity extends BaseEntity {
    private String name;
    private ActivityStatus status;
    private String authorId;

    public void updateEntity(UpdateWorkloadRq dto) {
        this.setName(dto.getName());
        this.setStatus(dto.getStatus());
        this.setAuthorId(dto.getAuthorId());
        this.setUpdatedAt(LocalDateTime.now());
    }
}