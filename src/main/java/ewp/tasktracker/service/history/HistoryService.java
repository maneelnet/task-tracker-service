package ewp.tasktracker.service.history;

import ewp.tasktracker.api.dto.history.CreateHistoryRq;
import ewp.tasktracker.api.dto.history.HistoryDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.history.UpdateHistoryRq;
import ewp.tasktracker.entity.history.HistoryEntity;

public interface HistoryService {

    HistoryEntity save(CreateHistoryRq dto);

    HistoryEntity findById(String id);

    PageDto<HistoryDto> findAll(int pageNumber, int pageSize);

    HistoryEntity update(UpdateHistoryRq dto);

    PageDto<HistoryDto> findByName(String name,int pageNumber,int pageSize);
}
