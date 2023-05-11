package ewp.tasktracker.service.workload;

import ewp.tasktracker.api.dto.workload.CreateWorkloadRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.workload.UpdateWorkloadRq;
import ewp.tasktracker.api.dto.workload.WorkloadDto;
import ewp.tasktracker.entity.workload.WorkloadEntity;

/**
 * Сервис для работы с рабочими пространствами
 */
public interface WorkloadService {

    WorkloadEntity save(CreateWorkloadRq dto);

    WorkloadEntity findById(String id);

    PageDto<WorkloadDto> findAll(Integer pageSize, Integer pageNumber);

    WorkloadEntity update(UpdateWorkloadRq dto);

}