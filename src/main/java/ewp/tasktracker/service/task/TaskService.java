package ewp.tasktracker.service.task;


import ewp.tasktracker.api.dto.task.CreateTaskRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.entity.task.TaskEntity;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public interface TaskService {
    TaskEntity save(CreateTaskRq dto);

    PageDto<TaskDto> findAll(Pageable pageable);

    TaskEntity getById(String id);

    TaskEntity deleteById(String id);

    TaskEntity update(TaskDto dto);

    PageDto<TaskDto> findAllByName(String name, Pageable pageable);

    PageDto<TaskDto> findAllByAssigneeId(String assigneeId, Pageable pageable);

    PageDto<TaskDto> getTaskEntitiesByProjectId(String projectId, LocalDateTime createdAt, Pageable pageable);
}
