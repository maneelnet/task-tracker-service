package ewp.tasktracker.repository.project;

import ewp.tasktracker.entity.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с проектами
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
}
