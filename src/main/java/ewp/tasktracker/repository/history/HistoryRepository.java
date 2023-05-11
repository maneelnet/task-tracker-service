package ewp.tasktracker.repository.history;

import ewp.tasktracker.entity.history.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с историями
 */
@Repository
public interface HistoryRepository extends JpaRepository <HistoryEntity, String> {

    Page<HistoryEntity> findByName(String name, Pageable pageable);
}
