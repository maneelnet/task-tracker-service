package ewp.tasktracker.repository.supersprint;

import ewp.tasktracker.entity.supersprint.SupersprintEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupersprintRepository extends JpaRepository<SupersprintEntity, String> {

    /**
     * Метод достает элементы с заданным name в заданном количестве
     */
    Page<SupersprintEntity> getByName(String name, Pageable pageable);
}
