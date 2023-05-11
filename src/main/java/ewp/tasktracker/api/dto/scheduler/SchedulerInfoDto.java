package ewp.tasktracker.api.dto.scheduler;

import ewp.tasktracker.entity.scheduler.SchedulerInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerInfoDto {
    private Long id;
    private LocalDateTime lasttriggerTime;

    public SchedulerInfoEntity toEntity() {
        return new SchedulerInfoEntity(
                this.id,
                this.lasttriggerTime
        );
    }
}
