package ewp.tasktracker.api.controller.task;

import ewp.tasktracker.api.dto.task.CreateTaskRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.entity.task.TaskEntity;
import ewp.tasktracker.service.task.TaskService;
import ewp.tasktracker.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequestMapping(value = "/api/task")
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"task"})
public class TaskController {
    private final TaskService taskService;
    private final PageUtil pageUtil;


    @PostMapping
    @ApiOperation(value = "Сохранить задачу", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<TaskDto> save(@RequestBody @Valid CreateTaskRq dto) {
        TaskEntity taskEntity = taskService.save(dto);
        return ResponseEntity.ok(new TaskDto(taskEntity));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить задачу по id", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<TaskDto> getById(@PathVariable String id) {
        TaskEntity taskEntity = taskService.getById(id);
        return ResponseEntity.ok(new TaskDto(taskEntity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить задачу по id", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<TaskDto> deleteTask(@PathVariable String id) {
        TaskEntity taskEntity = taskService.getById(id);
        if (taskEntity == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        taskService.deleteById(id);
        return ResponseEntity.ok(new TaskDto(taskEntity));
    }

    @GetMapping
    @ApiOperation(value = "Получить список задач", response = PageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })

    public ResponseEntity<PageDto<TaskDto>> getAll(@RequestParam(defaultValue = "20") int pageSize,
                                                   @RequestParam(defaultValue = "0") int pageNumber) {
        PageDto<TaskDto> taskPageDto = taskService.findAll(pageUtil.buildPageable(pageSize, pageNumber));
        return ResponseEntity.ok(taskPageDto);
    }

    @PutMapping
    @ApiOperation(value = "Обновить задачу", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<TaskDto> update(@RequestBody @Valid TaskDto taskDto) {
        TaskEntity taskEntity = taskService.update(taskDto);
        return ResponseEntity.ok(new TaskDto(taskEntity));
    }

    @GetMapping("/search")
    @ApiOperation(value = "Получить задачу по filter(name)", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<TaskDto>> getAllByName(@RequestParam String filter,
                                                         @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                         @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(taskService.findAllByName(filter, pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/getByUserId/{userId}")
    @ApiOperation(value = "Получить список задач по assignee id", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<TaskDto>> getAllByAssigneeId(@PathVariable String userId,
                                                               @RequestParam(defaultValue = "20") int pageSize,
                                                               @RequestParam(defaultValue = "0") int pageNumber) {
        PageDto<TaskDto> taskDtoPage = taskService.findAllByAssigneeId(userId, pageUtil.buildPageable(pageSize, pageNumber));
        return ResponseEntity.ok(taskDtoPage);
    }

    @GetMapping("/getByProjectId")
    @ApiOperation(value = "Получить список задач по project id", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<TaskDto>> getTasksByProjectId(@RequestParam String projectId,
                                                                @RequestParam("createdAt")
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdAt,
                                                                @RequestParam(defaultValue = "20") int pageSize,
                                                                @RequestParam(defaultValue = "0") int pageNumber) {
        PageDto<TaskDto> taskDtoPage = taskService.getTaskEntitiesByProjectId(projectId, createdAt, pageUtil.buildPageable(pageSize, pageNumber));
        return ResponseEntity.ok(taskDtoPage);
    }
}

