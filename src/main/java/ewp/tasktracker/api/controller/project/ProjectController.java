package ewp.tasktracker.api.controller.project;

import ewp.tasktracker.api.dto.project.CreateProjectRq;
import ewp.tasktracker.api.dto.project.ProjectDto;
import ewp.tasktracker.api.dto.project.UpdateProjectRq;
import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.service.project.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/project")
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"projects"})
@Slf4j
public class ProjectController {
    private final ProjectService projectService;

    @ApiOperation(value = "Сохранить проект", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    @PostMapping
    public ResponseEntity<ProjectDto> save(@Valid @RequestBody CreateProjectRq dto) {
        ProjectEntity projectEntity = projectService.save(dto);
        return ResponseEntity.ok(new ProjectDto(projectEntity));
    }

    @ApiOperation(value = "Получить проект по id", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Не найдено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable String id) {
        ProjectEntity projectEntity = projectService.findById(id);
        return ResponseEntity.ok(new ProjectDto(projectEntity));
    }

    @ApiOperation(value = "Получить список всех проектов", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "Error 422"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAll() {
        List<ProjectDto> projectDtos = projectService.findAll().stream().map(ProjectDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(projectDtos);
    }

    @ApiOperation(value = "Удалить проект по id", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Не найдено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDto> deleteById(@PathVariable String id) {
        if (projectService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    @ApiOperation(value = "Обновить проект по id", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Проект не найден"),
            @ApiResponse(code = 422, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<ProjectDto> update(@Valid @RequestBody UpdateProjectRq updateProjectRq) {
        ProjectEntity projectEntity = projectService.update(updateProjectRq);
        return ResponseEntity.ok(new ProjectDto(projectEntity));
    }
}


