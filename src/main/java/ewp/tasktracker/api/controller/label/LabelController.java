package ewp.tasktracker.api.controller.label;

import ewp.tasktracker.api.dto.label.CreateLabelRq;
import ewp.tasktracker.api.dto.label.LabelDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.service.label.LabelService;
import ewp.tasktracker.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с метками задачи
 */

@RestController
@RequestMapping(value = "/api/label",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"label"})
public class LabelController {
    private final LabelService labelService;
    private final PageUtil pageUtil;

    /**
     * Сохраняем нашу метку в бд
     */
    @PostMapping
    @ApiOperation(value = "Сохранить метку", response = LabelDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<LabelDto> save(@RequestBody CreateLabelRq dto) {
        return ResponseEntity.ok(labelService.save(dto));
    }

    /**
     * taskId = нужные нам метки по определенной задаче
     * pageNumber = номер страницы при делении количества меток по pageSize
     * pageSize = количество меток на нашей странице
     * Параметры передаются в сервисный слой и там проверяются, здесь не стал делать проверки.
     */
    @GetMapping
    @ApiOperation(value = "Получить список меток задачи", response = PageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "422 Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<LabelDto>> getLabelsForTaskIdAndSizeOrElseGetLabelsForSize(@RequestParam (required = false) String taskId,
                                                                                             @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                                                             @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(labelService.findAllByPageRequest(taskId, pageUtil.buildPageable(pageSize, pageNumber)));
    }


    /**
     * Удаляет элемент по Id, если элемента нет возвращает клиенту 404 ошибку not found и "Label " + id + " not found!"
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить метку задачи", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Label not Found"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<LabelDto> getRemoveLabel(@PathVariable String id) {
            return new ResponseEntity<>(labelService.deleteLabelEntityById(id), HttpStatus.OK); // 200
    }

    /**
     * Обновляет метку, принимает LabelDto по основному адресу("/api/label")"
     */
    @PutMapping
    @ApiOperation(value = "Обновить метку по id", response = LabelDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Метка не найдена"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<LabelDto> updateById(@RequestBody LabelDto dto) {
        return ResponseEntity.ok(labelService.updateLabelFromController(dto));
    }
}