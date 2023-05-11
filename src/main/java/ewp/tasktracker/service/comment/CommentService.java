package ewp.tasktracker.service.comment;

import ewp.tasktracker.api.dto.comment.CommentDto;
import ewp.tasktracker.api.dto.comment.CreateCommentRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.comment.UpdateCommentRq;
import ewp.tasktracker.entity.comment.CommentEntity;

public interface CommentService {

    CommentEntity save(CreateCommentRq dto);

    CommentEntity findById(String commentId);

    CommentEntity update(UpdateCommentRq dto);

    boolean deleteById(String id);

    PageDto<CommentDto> findAllByTaskTd(String taskId, Integer pageSize, Integer pageNumber);
}
