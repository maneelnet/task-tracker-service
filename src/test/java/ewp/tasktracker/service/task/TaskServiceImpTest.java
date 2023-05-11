package ewp.tasktracker.service.task;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.task.CreateTaskRq;
import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.entity.task.TaskEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.repository.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImpTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImp taskService;

    @Test
    void should_save_one_task() {
        CreateTaskRq createTaskRq=new CreateTaskRq();
        createTaskRq.setName("some name");
        createTaskRq.setDescription("some description");
        createTaskRq.setStatus(ProgressStatus.DONE);
        createTaskRq.setPriority(Priority.LOW);
        createTaskRq.setHistoryId("some id");
        createTaskRq.setAuthorId("some id author");
        createTaskRq.setAssigneeId("some id assignee");

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(createTaskRq.toEntity());



        TaskEntity taskEntity= taskService.save(createTaskRq);
        Assert.notNull(new TaskDto(taskEntity));
        verify(taskRepository,times(1)).save(any(TaskEntity.class));
    }

    @Test
    void should_find_task_by_id() {
        TaskEntity taskEntity=new TaskEntity();
        taskEntity.setName("some name");
        taskEntity.setDescription("some description");
        taskEntity.setStatus(ProgressStatus.DONE);
        taskEntity.setPriority(Priority.LOW);
        taskEntity.setHistoryId("some id");
        taskEntity.setAuthorId("some id author");
        taskEntity.setAssigneeId("some id assignee");

        when(taskRepository.findById(anyString())).thenReturn(Optional.of(taskEntity));
        Optional<TaskEntity> actualTask = taskRepository.findById(UUID.randomUUID().toString());

        assertThat(actualTask.get()).usingRecursiveComparison().isEqualTo(taskEntity);
        verify(taskRepository, times(1)).findById(anyString());
    }

    @Test
    void should_delete_task_by_id() {
        TaskEntity taskEntity = new TaskEntity();
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(taskEntity));
        doNothing().when(taskRepository).deleteById(anyString());

        TaskEntity deletedTaskEntity = taskService.deleteById(taskEntity.getId());
        assertThat(taskEntity).isEqualTo(deletedTaskEntity);

        verify(taskRepository, times(1)).deleteById(anyString());
    }


    @Test
    void should_find_task_by_name() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName("some name");

        Page<TaskEntity> page = new PageImpl<>(List.of(taskEntity), PageRequest.of(0, 20), 2);
        when(taskRepository.getByName("some name",PageRequest.of(0, 20))).thenReturn(page);
        PageDto<TaskDto> pageDto = taskService.findAllByName("some name", PageRequest.of(0, 20));

        assertThat(pageDto.getItems().get(0).getName()).isEqualTo(page.getContent().get(0).getName());
        verify(taskRepository, times(1)).getByName("some name",PageRequest.of(0, 20));
    }

    @Test
    void should_find_all_task() {
        Page<TaskEntity> page = new PageImpl<>(List.of(new TaskEntity(), new TaskEntity()), PageRequest.of(0, 20), 2);
        when(taskRepository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        PageDto<TaskDto> pageDto = taskService.findAll(PageRequest.of(0, 20));

        assertThat(pageDto.getItems().size()).isEqualTo(page.getTotalElements());
        verify(taskRepository, times(1)).findAll(PageRequest.of(0, 20));
    }

    @Test
    void should_update_task() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName("some name");
        taskEntity.setDescription("some desc");

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(taskEntity));

        taskEntity.setName("Some name");
        taskEntity.setDescription("unit test");

        TaskEntity updatedEntity = taskService.update(new TaskDto(taskEntity));

        assertThat(updatedEntity.getName()).isEqualTo("Some name");
        assertThat(updatedEntity.getDescription()).isEqualTo("unit test");

        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }


}