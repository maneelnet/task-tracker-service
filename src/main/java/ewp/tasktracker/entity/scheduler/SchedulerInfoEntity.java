package ewp.tasktracker.entity.scheduler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "scheduler_info")
public class SchedulerInfoEntity {
    @Id
    private Long id;
    private LocalDateTime lasttriggerTime;
}
