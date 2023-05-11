package ewp.tasktracker.service.scheduler;

import ewp.tasktracker.api.dto.scheduler.SchedulerInfoDto;
import ewp.tasktracker.entity.scheduler.SchedulerInfoEntity;
import ewp.tasktracker.repository.scheduler.SchedulerInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchedulerInfoServiceImpl implements SchedulerInfoService {
    private final SchedulerInfoRepository schedulerInfoRepository;

    @Override
    public SchedulerInfoEntity save(SchedulerInfoDto dto) {
        return schedulerInfoRepository.save(dto.toEntity());
    }
}
