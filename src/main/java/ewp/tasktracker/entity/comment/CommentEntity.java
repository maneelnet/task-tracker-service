package ewp.tasktracker.entity.comment;

import ewp.tasktracker.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class CommentEntity extends BaseEntity {
    private String text;
    private String authorId;
    private String taskId;

    public CommentEntity(String text) {
        this.text = text;
    }
}
