package ewp.tasktracker.api.controller.workload;

import ewp.tasktracker.api.dto.workload.CreateWorkloadRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.workload.UpdateWorkloadRq;
import ewp.tasktracker.api.dto.workload.WorkloadDto;
import ewp.tasktracker.entity.workload.WorkloadEntity;
import ewp.tasktracker.service.workload.WorkloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/workload")
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"workload"})
public class WorkloadController {

    private final WorkloadService workloadService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Сохранить рабочее пространство", response = WorkloadDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<WorkloadDto> save(@Valid @RequestBody CreateWorkloadRq dto) {
        WorkloadEntity workloadEntity = workloadService.save(dto);
        return ResponseEntity.ok(new WorkloadDto(workloadEntity));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Получить рабочее пространство по id", response = WorkloadDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<WorkloadDto> getById(@PathVariable String id) {
        WorkloadEntity workloadEntity = workloadService.findById(id);
        return ResponseEntity.ok(new WorkloadDto(workloadEntity));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Получить список рабочих пространств", response = PageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<WorkloadDto>> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
                                                       @RequestParam(defaultValue = "0") Integer pageNumber) {
        PageDto<WorkloadDto> workloads = workloadService.findAll(pageSize, pageNumber);
        return ResponseEntity.ok(workloads);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Редактировать рабочее пространство", response = WorkloadDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность для обновления не найдена"),
            @ApiResponse(code = 422, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<WorkloadDto> update(@Valid @RequestBody UpdateWorkloadRq dto) {
        WorkloadEntity workloadEntity = workloadService.update(dto);
        return ResponseEntity.ok(new WorkloadDto(workloadEntity));
    }
}
