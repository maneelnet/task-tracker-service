package ewp.tasktracker.api.controller.epic;

import ewp.tasktracker.api.dto.epic.CreateEpicRq;
import ewp.tasktracker.api.dto.epic.EpicDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.service.epic.EpicService;
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
@RequestMapping(value = "/api/epic",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"epic"})
public class EpicController {

    private final EpicService epicService;
    private final PageUtil pageUtil;

    @GetMapping
    @ApiOperation(value = "Получить список эпиков", response = PageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "422 Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<EpicDto>> getAll(@RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(epicService.findAllByPageRequest(pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/search")
    @ApiOperation(value = "Получить эпик по filter(name)", response = PageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<EpicDto>> getAllByName(@RequestParam String filter,
                                                           @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                           @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(epicService.findAllByName(filter, pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить эпик по id", response = EpicDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<EpicDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(epicService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Сохранить эпик", response = EpicDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<EpicDto> save(@RequestBody @Validated CreateEpicRq dto) {
        return ResponseEntity.ok(epicService.save(dto));
    }

    @PutMapping
    @ApiOperation(value = "Обновить эпик по id", response = EpicDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<EpicDto> updateById(@RequestBody @Validated EpicDto dto) {
        return ResponseEntity.ok(epicService.updateEpicFromController(dto));
    }
}