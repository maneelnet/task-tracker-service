package ewp.tasktracker.repository.workload;

import ewp.tasktracker.entity.workload.WorkloadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с рабочими пространствами
 */
@Repository
public interface WorkloadRepository extends JpaRepository<WorkloadEntity, String> {
}