package ewp.tasktracker.service.scheduler;

import ewp.tasktracker.api.dto.scheduler.SchedulerInfoDto;
import ewp.tasktracker.entity.scheduler.SchedulerInfoEntity;

public interface SchedulerInfoService {

    SchedulerInfoEntity save(SchedulerInfoDto dto);

}
