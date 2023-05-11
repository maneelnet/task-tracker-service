package ewp.tasktracker.api.controller.supersprint;

import ewp.tasktracker.api.dto.supersprint.CreateSupersprintRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.supersprint.SupersprintDto;
import ewp.tasktracker.service.supersprint.SupersprintService;
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
@RequestMapping(value = "/api/supersprint",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"supersprint"})
public class SupersprintController {

    private final SupersprintService service;
    private final PageUtil pageUtil;

    @GetMapping
    @ApiOperation(value = "Получить список суперспринтов", response = SupersprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "422 Unprocessable Supersprint"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<SupersprintDto>> getAll(@RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                          @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllByPageRequest(pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/search")
    @ApiOperation(value = "Получить суперспринт по filter(name)", response = SupersprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<SupersprintDto>> getAllByName(@RequestParam String filter,
                                                                @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                                @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllByName(filter, pageUtil.buildPageable(pageSize, pageNumber)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить суперспринт по id", response = SupersprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<SupersprintDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Сохранить суперспринт", response = SupersprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<SupersprintDto> save(@RequestBody @Validated CreateSupersprintRq dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping
    @ApiOperation(value = "Обновить суперспринт по id", response = SupersprintDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<SupersprintDto> updateById(@RequestBody @Validated SupersprintDto dto) {
        return ResponseEntity.ok(service.updateSupersprintFromController(dto));
    }
}
