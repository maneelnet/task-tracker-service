package ewp.tasktracker.api.controller.history;

import ewp.tasktracker.api.dto.history.CreateHistoryRq;
import ewp.tasktracker.api.dto.history.HistoryDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.history.UpdateHistoryRq;
import ewp.tasktracker.entity.history.HistoryEntity;
import ewp.tasktracker.service.history.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/history")
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"history"})
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @ApiOperation(value = "Получить список историй", response = HistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<HistoryDto>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                      @RequestParam(defaultValue = "20") int pageSize) {
        PageDto<HistoryDto> histories = historyService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить историю по id", response = HistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<HistoryDto> getById(@PathVariable String id) {
        HistoryEntity historyEntity = historyService.findById(id);
        return ResponseEntity.ok(new HistoryDto(historyEntity));
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Получить историю по имени", response = HistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<HistoryDto>> getByName(String name,
                                                @RequestParam(defaultValue = "0") int pageNumber,
                                                @RequestParam(defaultValue = "20") int pageSize) {
        PageDto<HistoryDto> histories = historyService.findByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(histories);
    }


    @PutMapping
    @ApiOperation(value = "Обновить историю по id", response = HistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "История не найдена"),
            @ApiResponse(code = 422, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<HistoryDto> update(@RequestBody UpdateHistoryRq dto) {
        HistoryEntity historyEntity = historyService.update(dto);
        return ResponseEntity.ok(new HistoryDto(historyEntity));
    }

    @PostMapping
    @ApiOperation(value = "Сохранить историю", response = HistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<HistoryDto> save(@Valid @RequestBody CreateHistoryRq dto) {
        HistoryEntity historyEntity = historyService.save(dto);
        return ResponseEntity.ok(new HistoryDto(historyEntity));
    }
}
