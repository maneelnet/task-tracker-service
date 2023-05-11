package ewp.tasktracker.repository.epic;

import ewp.tasktracker.entity.epic.EpicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с эпиками
 */
@Repository
public interface EpicRepository extends JpaRepository<EpicEntity, String> {
    /**
     * Метод достает элементы с заданным name в заданном количестве
     */
    Page<EpicEntity> getByName(String name, Pageable pageable);
}
