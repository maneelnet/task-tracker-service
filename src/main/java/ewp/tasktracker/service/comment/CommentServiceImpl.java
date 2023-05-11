package ewp.tasktracker.service.comment;

import ewp.tasktracker.api.dto.comment.CommentDto;
import ewp.tasktracker.api.dto.comment.CreateCommentRq;
import ewp.tasktracker.api.dto.comment.UpdateCommentRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.comment.CommentEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.comment.CommentRepository;
import ewp.tasktracker.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PageUtil pageUtil;
    @Override
    public CommentEntity save(CreateCommentRq dto) {
        return commentRepository.save(dto.toEntity());
    }

    @Override
    public CommentEntity findById(String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
    }

    @Override
    @Transactional
    public CommentEntity update(UpdateCommentRq dto) {
        CommentEntity commentEntity = findById(dto.getId());
        commentEntity.setText(dto.getText());
        commentEntity.setUpdatedAt(LocalDateTime.now());

        return commentEntity;
    }

    @Override
    public PageDto<CommentDto> findAllByTaskTd(String taskId, Integer pageSize, Integer pageNumber) {
        Page<CommentEntity> commentsPage = commentRepository.findAllByTaskId(taskId, pageUtil.buildPageable(pageSize, pageNumber));
        List<CommentDto> commentDtoList = commentsPage.getContent()
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());

        return new PageDto<>(
                commentDtoList,
                commentsPage.getNumber(),
                commentsPage.getSize(),
                commentsPage.getTotalPages()
        );
    }

    @Transactional
    public boolean deleteById(String id) {
        if (commentRepository.findById(id).isEmpty()) {
            return false;
        }

        commentRepository.deleteById(id);

        return true;
    }
}
