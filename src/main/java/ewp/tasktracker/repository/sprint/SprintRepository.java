package ewp.tasktracker.repository.sprint;

import ewp.tasktracker.entity.sprint.SprintEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<SprintEntity, String> {

    /**
     * Метод достает элементы с заданным name в заданном количестве
     */
    Page<SprintEntity> getByName(String name, Pageable pageable);
}
