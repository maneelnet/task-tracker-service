package ewp.tasktracker.api.controller.bug;

import ewp.tasktracker.api.dto.bug.BugDto;
import ewp.tasktracker.api.dto.bug.CreateBugRq;
import ewp.tasktracker.api.dto.bug.UpdateBugRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.config.TaskTrackerProperties;
import ewp.tasktracker.entity.bug.BugEntity;
import ewp.tasktracker.service.bug.BugService;
import ewp.tasktracker.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/bug")
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"bug"})
public class BugController {
    private final BugService bugService;
    private final PageUtil pageUtil;
    private final TaskTrackerProperties properties;

    @GetMapping
    @ApiOperation(value = "Получить список багов", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<BugDto>> getAll(@RequestParam(defaultValue="0") int pageSize,
                                                  @RequestParam(defaultValue="0") int pageNumber)
    {
        if (pageSize == 0) {
            pageSize = properties.getPageDefaultSize();
        }
        PageDto<BugDto> bugPageDto = bugService.findAll(pageUtil.buildPageable(pageSize, pageNumber));
        return ResponseEntity.ok(bugPageDto);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Получить список багов по имени", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<BugDto>> getAll(@RequestParam String filter,
                                                  @RequestParam(defaultValue="0") int pageSize,
                                                  @RequestParam(defaultValue="0") int pageNumber)
    {
        if (pageSize == 0) {
            pageSize = properties.getPageDefaultSize();
        }
        PageDto<BugDto> bugPageDto = bugService.findAllByName(filter, pageUtil.buildPageable(pageSize, pageNumber));
        return ResponseEntity.ok(bugPageDto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить баг по id", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<BugDto> getById(@PathVariable String id) {
        BugEntity bugEntity = bugService.findById(id);
        return ResponseEntity.ok(new BugDto(bugEntity));
    }

    @PostMapping
    @ApiOperation(value = "Сохранить баг", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<BugDto> save(@RequestBody @Validated CreateBugRq dto) {
        BugEntity bugEntity = bugService.save(dto);
        return ResponseEntity.ok(new BugDto(bugEntity));
    }

    @PutMapping
    @ApiOperation(value = "Обновить баг", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<BugDto> update(@RequestBody @Validated UpdateBugRq updateBugRq) {
        BugEntity bugEntity = bugService.update(updateBugRq);
        return ResponseEntity.ok(new BugDto(bugEntity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить баг по id", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Сущность не найдена"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<BugDto> delete(@PathVariable String id) {
        BugEntity bugEntity = bugService.deleteById(id);
        return ResponseEntity.ok(new BugDto(bugEntity));
    }

    @GetMapping("/getByUserId/{userId}")
    @ApiOperation(value = "Получить список багов по assignee id", response = BugDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутрянняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<BugDto>> getAllByAssigneeId(@PathVariable String userId,
                                                              @RequestParam(defaultValue="0") int pageSize,
                                                              @RequestParam(defaultValue="0") int pageNumber)
    {
        if (pageSize == 0) {
            pageSize = properties.getPageDefaultSize();
        }
        PageDto<BugDto> bugPageDto = bugService.findAllByAssigneeId(userId, pageUtil.buildPageable(pageSize, pageNumber));
        return ResponseEntity.ok(bugPageDto);
    }
}
