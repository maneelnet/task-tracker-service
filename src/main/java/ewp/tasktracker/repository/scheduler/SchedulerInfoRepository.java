package ewp.tasktracker.repository.scheduler;

import ewp.tasktracker.entity.scheduler.SchedulerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerInfoRepository extends JpaRepository<SchedulerInfoEntity, Long> {
}
