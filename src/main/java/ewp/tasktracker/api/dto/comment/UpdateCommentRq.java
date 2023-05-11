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
public class UpdateCommentRq {
    @NotNull(message = "ID must not be null")
    @NotBlank(message = "ID must not be empty")
    private String id;
    @NotNull(message = "Text must not be null")
    @NotBlank(message = "Text must not be empty")
    private String text;

    public CommentEntity toEntity() {
        return new CommentEntity(this.text);
    }
}
