package ewp.tasktracker.service.task;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.task.CreateTaskRq;
import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.entity.task.TaskEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.task.TaskRepository;
import ewp.tasktracker.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final PageUtil pageUtil;


    @Override
    public TaskEntity save(CreateTaskRq dto) {
        return taskRepository.save(dto.toEntity());
    }

    @Override
    public PageDto<TaskDto> findAll(Pageable pageable) {
        Page<TaskEntity> taskPage = taskRepository.findAll(pageable);
        List<TaskDto> taskDtoList = taskPage.getContent().stream().map(TaskDto::new).collect(Collectors.toList());
        return new PageDto<>(taskDtoList, taskPage.getNumber(), taskPage.getSize(), taskPage.getTotalPages());
    }


    @Override
    public TaskEntity getById(String id) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        return taskEntity.orElseThrow(() -> new ResourceNotFoundException("Task not found, id: " + id));
    }


    @Override
    public TaskEntity deleteById(String id) {
        TaskEntity taskEntity = getById(id);
        taskRepository.deleteById(id);
        return taskEntity;
    }


    @Override
    public TaskEntity update(TaskDto dto) {
        TaskEntity taskEntity = getById(dto.getId());
        taskEntity.updateTask(dto);
        taskEntity.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(taskEntity);
        return taskEntity;
    }

    @Override
    public PageDto<TaskDto> findAllByName(String name, Pageable pageable) {
        Page<TaskEntity> taskEntities = taskRepository.getByName(name, pageable);
        return new PageDto<>(taskEntities.getContent().stream()
                .map(taskEntity -> TaskDto.from(taskEntity)).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) taskEntities.getTotalElements());
    }

    @Override
    public PageDto<TaskDto> findAllByAssigneeId(String assigneeId, Pageable pageable) {
        Page<TaskEntity> taskPage = taskRepository.getByAssigneeId(assigneeId, pageable);
        List<TaskDto> taskDtoList = taskPage.getContent().stream().map(TaskDto::new).collect(Collectors.toList());
        return new PageDto<>(taskDtoList, taskPage.getNumber(), taskPage.getSize(), taskPage.getTotalPages());
    }

    @Override
    public PageDto<TaskDto> getTaskEntitiesByProjectId(String projectId, LocalDateTime createdAt, Pageable pageable) {
        Page<TaskEntity> taskPage = taskRepository.getTaskEntitiesByProjectId(projectId, createdAt, pageable);
        List<TaskDto> taskDtoList = taskPage.getContent().stream().map(TaskDto::new).collect(Collectors.toList());
        return new PageDto<>(taskDtoList, taskPage.getNumber(), taskPage.getSize(), taskPage.getTotalPages());
    }
}

