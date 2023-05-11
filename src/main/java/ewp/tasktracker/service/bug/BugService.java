package ewp.tasktracker.service.bug;

import ewp.tasktracker.api.dto.bug.BugDto;
import ewp.tasktracker.api.dto.bug.CreateBugRq;
import ewp.tasktracker.api.dto.bug.UpdateBugRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.bug.BugEntity;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BugService {

    BugEntity save(CreateBugRq dto);

    PageDto<BugDto> findAll(Pageable pageable);

    BugEntity findById(String id);

    BugEntity update(UpdateBugRq dto);

    BugEntity deleteById(String id);

    PageDto<BugDto> findAllByName(String name, Pageable pageable);

    PageDto<BugDto> findAllByAssigneeId(String assigneeId, Pageable pageable);

    PageDto<BugDto> findAllByProjectIdAndDate(String projectId, LocalDateTime createdDate, Pageable pageable);

}
