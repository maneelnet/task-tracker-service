package ewp.tasktracker.api.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewp.tasktracker.entity.comment.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String id;
    private String text;
    private String authorId;
    private String taskId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;


    public CommentDto(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.text = commentEntity.getText();
        this.authorId = commentEntity.getAuthorId();
        this.taskId = commentEntity.getTaskId();
        this.createdAt = commentEntity.getCreatedAt();
        this.updatedAt = commentEntity.getUpdatedAt();
    }


}
