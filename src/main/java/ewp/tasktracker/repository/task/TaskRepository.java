package ewp.tasktracker.repository.task;

import ewp.tasktracker.entity.task.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    Page<TaskEntity> getByName(String name, Pageable pageable);

    Page<TaskEntity> getByAssigneeId(String assigneeId, Pageable pageable);

    @Query(value = "SELECT * FROM tasks t  JOIN histories h ON t.history_id = h.id  JOIN epics e on h.epic_id = e.id WHERE e.project_id =:id AND t.created_at >=:createdAt",
            nativeQuery = true)
    Page<TaskEntity> getTaskEntitiesByProjectId(@Param("id") String projectId, @Param("createdAt") LocalDateTime createdAt, Pageable pageable);
}
