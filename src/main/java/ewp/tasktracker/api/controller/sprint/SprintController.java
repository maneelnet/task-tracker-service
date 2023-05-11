package ewp.tasktracker.api.controller.sprint;

import ewp.tasktracker.api.dto.sprint.CreateSprintRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.sprint.SprintDto;
import ewp.tasktracker.service.sprint.SprintService;
import ewp.tasktracker.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/sprint",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"sprint"})
public class SprintController {

    private final SprintService service;
    private final PageUtil pageUtil;

    @GetMapping
    @ApiOperation(value = "Получить список спринтов", response = SprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "422 Unprocessable Sprint"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<SprintDto>> getAll(@RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                     @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllByPageRequest(pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/search")
    @ApiOperation(value = "Получить спринт по filter(name)", response = SprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<SprintDto>> getAllByName(@RequestParam String filter,
                                                           @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                           @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllByName(filter, pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить спринт по id", response = SprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<SprintDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Сохранить спринт", response = SprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<SprintDto> save(@RequestBody @Validated CreateSprintRq dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping
    @ApiOperation(value = "Обновить спринт по id", response = SprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<SprintDto> updateById(@RequestBody @Validated SprintDto dto) {
        return ResponseEntity.ok(service.updateSprintFromController(dto));
    }
}
