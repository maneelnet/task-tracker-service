package ewp.tasktracker.repository.bug;

import ewp.tasktracker.entity.bug.BugEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BugRepository extends JpaRepository<BugEntity, String> {

    Page<BugEntity> getByName(String name, Pageable pageable);

    Page<BugEntity> getByAssigneeId(String assigneeId, Pageable pageable);

    @Query(value = "SELECT * FROM bugs\n" +
            "    LEFT JOIN histories h on bugs.history_id = h.id\n" +
            "    LEFT JOIN epics e on h.epic_id = e.id\n" +
            "    WHERE e.project_id = ?1 AND bugs.created_at >= ?2", nativeQuery = true)
    Page<BugEntity> getByProjectIdAndDate(String projectId,
                                          LocalDateTime createdDate,
                                          Pageable pageable);

}
