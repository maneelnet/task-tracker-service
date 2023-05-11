package ewp.tasktracker.repository.comment;

import ewp.tasktracker.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    Page<CommentEntity> findAllByTaskId(String taskId, Pageable pageable);
}
