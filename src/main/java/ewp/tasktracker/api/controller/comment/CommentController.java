package ewp.tasktracker.api.controller.comment;

import ewp.tasktracker.api.dto.comment.CommentDto;
import ewp.tasktracker.api.dto.comment.CreateCommentRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.comment.UpdateCommentRq;
import ewp.tasktracker.entity.comment.CommentEntity;
import ewp.tasktracker.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/comments")
@AllArgsConstructor
@Api(value = "task-tracker", tags = {"comment"})
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ApiOperation(value = "Сохранить комментарий", response = CommentEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Успешный ответ"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<CommentDto> save(@Valid @RequestBody CreateCommentRq dto) {
        HttpStatus status = HttpStatus.CREATED;
        CommentEntity commentEntity = commentService.save(dto);
        return new ResponseEntity<>(new CommentDto(commentEntity), status);

    }

    /**
     * Обновляет текст комментария, созраняет дату и время обновления.
     */
    @PutMapping
    @ApiOperation(value = "Обновить комментарий по id", response = CommentEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Комментарий не найден"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<CommentDto> update(@Valid @RequestBody UpdateCommentRq dto) {
        HttpStatus status = HttpStatus.ACCEPTED;
        CommentEntity commentEntity = commentService.update(dto);
        return new ResponseEntity<>(new CommentDto(commentEntity), status);
    }

    /*
     * Удаляет элемент по Id, если элемента нет возвращает клиенту 404 ошибку not found"
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить комментарий задачи", response = CommentEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Успешный ответ"),
            @ApiResponse(code = 404, message = "Элемент не найден"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<CommentDto> delete(@PathVariable String id) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (commentService.deleteById(id)) {
            return new ResponseEntity<CommentDto>(status);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    /**
     * taskId = получить список комментариев по идентификатору задачи
     */
    @GetMapping
    @ApiOperation(value = "Получить список комментариев задачи", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный ответ"),
            @ApiResponse(code = 422, message = "422 Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервиса")
    })
    public ResponseEntity<PageDto<CommentDto>> getAll(@RequestParam String taskId,
                                                      @RequestParam(defaultValue = "20") Integer pageSize,
                                                      @RequestParam(defaultValue = "0") Integer pageNumber) {
        PageDto<CommentDto> comments = commentService.findAllByTaskTd(taskId, pageSize, pageNumber);
        return ResponseEntity.ok(comments);
    }
}
