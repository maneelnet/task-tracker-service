package ewp.tasktracker.repository.label;

import ewp.tasktracker.entity.label.LabelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с метками задачи
 */
@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, String> {

    /**
     * Метод достает элементы с заданным taskId в заданном количестве
     */
    Page<LabelEntity> findAllByTaskId(String taskId, Pageable pageable);

}
