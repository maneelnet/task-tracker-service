package ewp.tasktracker.api.controller.notification;

import ewp.tasktracker.api.dto.notification.CreateNotificationSubscriptionRq;
import ewp.tasktracker.api.dto.notification.NotificationSubscriptionDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.response.ResponseDto;
import ewp.tasktracker.service.notification.NotificationSubscriptionService;
import ewp.tasktracker.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"notification"})
public class NotificationSubscriptionController {

    private final NotificationSubscriptionService service;
    private final PageUtil pageUtil;

    /**
     * Подписываем на уведомления
     */
    @PostMapping("/subscribe")
    @ApiOperation(value = "Подписаться на уведомления", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<ResponseDto> subscribe(@RequestBody CreateNotificationSubscriptionRq dto) {
        return ResponseEntity.ok(service.subscribeOnNotifications(dto));
    }

    /**
     * Отписываемся от уведомлений
     */
    @PostMapping("/unsubscribe")
    @ApiOperation(value = "Отписаться от уведомлений", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<ResponseDto> unsubscribe(@RequestBody NotificationSubscriptionDto dto) {
        return ResponseEntity.ok(service.unsubscribeOnNotifications(dto));
    }

    @GetMapping
    @ApiOperation(value = "Получить список подписчиков", response = PageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "422 Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<NotificationSubscriptionDto>> getAll(@RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllByPageRequest(pageUtil.buildPageable(pageSize, pageNumber)));
    }
}
