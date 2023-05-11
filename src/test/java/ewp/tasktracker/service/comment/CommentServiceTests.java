package ewp.tasktracker.service.comment;

import ewp.tasktracker.api.dto.comment.CommentDto;
import ewp.tasktracker.api.dto.comment.CreateCommentRq;
import ewp.tasktracker.api.dto.comment.UpdateCommentRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.comment.CommentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional // roll back
public class CommentServiceTests {
    @Autowired
    private CommentService commentService;

    @Test
    public void testCreateComment() {
        CreateCommentRq dto = new CreateCommentRq("Good job!", "123", "456");
        CommentEntity savedComment = commentService.save(dto);

        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getId()).isNotBlank();

        assertThat(savedComment.getText()).isNotNull();
        assertThat(savedComment.getText()).isNotBlank();

        assertThat(savedComment.getAuthorId()).isNotNull();
        assertThat(savedComment.getAuthorId()).isNotBlank();

        assertThat(savedComment.getTaskId()).isNotNull();
        assertThat(savedComment.getTaskId()).isNotBlank();

        LocalDateTime time = LocalDateTime.now();
        assertThat(time.getYear()).isEqualTo(savedComment.getCreatedAt().getYear());
        assertThat(time.getMonth().equals(savedComment.getCreatedAt().getMonth()));
        assertThat(time.getDayOfMonth()).isEqualTo(savedComment.getCreatedAt().getDayOfMonth());
        assertThat(time.getHour()).isEqualTo(savedComment.getCreatedAt().getHour());
        assertThat(time.getMinute()).isEqualTo(savedComment.getCreatedAt().getMinute());
    }

    @Test
    public void testFindById() {
        CreateCommentRq dto = new CreateCommentRq("Good job!", "123", "456");
        CommentEntity savedComment = commentService.save(dto);

        String id = savedComment.getId();
        CommentEntity extractedComment = commentService.findById(id);

        assertThat(savedComment.getId().equals(extractedComment.getId()));

        String wrongId = "777";
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> commentService.findById(wrongId))
                .withMessage("Comment not found with id: " + wrongId);
    }

    @Test
    public void testUpdateComment() {
        CreateCommentRq createDto = new CreateCommentRq("Good job!", "123", "456");
        CommentEntity savedComment = commentService.save(createDto);

        UpdateCommentRq updateDto = new UpdateCommentRq(savedComment.getId(), "Updated comment");
        commentService.update(updateDto);

        CommentEntity updatedComment = commentService.findById(savedComment.getId());

        assertThat(updatedComment.getText()).isEqualTo("Updated comment");

        LocalDateTime time = LocalDateTime.now();
        assertThat(time.getYear()).isEqualTo(updatedComment.getUpdatedAt().getYear());
        assertThat(time.getMonth().equals(updatedComment.getUpdatedAt().getMonth()));
        assertThat(time.getDayOfMonth()).isEqualTo(updatedComment.getUpdatedAt().getDayOfMonth());
        assertThat(time.getHour()).isEqualTo(updatedComment.getUpdatedAt().getHour());
        assertThat(time.getMinute()).isEqualTo(updatedComment.getUpdatedAt().getMinute());
    }

    @Test
    public void testFindAllByTaskId() {
        int pageNumber = 0;
        int pageSize = 4;

        PageDto<CommentDto> pageDto
                = commentService.findAllByTaskTd("456", pageSize, pageNumber);

        pageDto.getItems().stream().forEach(item -> System.out.println(item.getText()));

        assertThat(pageDto.getItems().size()).isEqualTo(pageSize);
    }

    @Test
    public void testDeleteById() {
        String delId = "35fd536b-077e-4dda-b340-532cf90354db";
        boolean deleted = commentService.deleteById(delId);
        assertThat(deleted).isTrue();

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> commentService.findById(delId))
                .withMessage("Comment not found with id: " + delId);
    }
}
