package ewp.tasktracker.api.dto.comment;

import ewp.tasktracker.entity.comment.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRq {
    @NotNull(message = "Text must not be null")
    @NotBlank(message = "Text must not be empty")
    private String text;
    @NotNull(message = "Author id must not be null")
    @NotBlank(message = "Author id must not be blank")
    private String authorId;
    @NotNull(message = "Task id must not be null")
    @NotBlank(message = "Task id must not be blank")
    private String taskId;

    public CommentEntity toEntity() {
        return new CommentEntity(this.text,
                                 this.authorId,
                                 this.taskId);
    }

}
