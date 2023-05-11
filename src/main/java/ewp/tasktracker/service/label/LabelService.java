package ewp.tasktracker.service.label;

import ewp.tasktracker.api.dto.label.CreateLabelRq;
import ewp.tasktracker.api.dto.label.LabelDto;
import ewp.tasktracker.api.dto.response.PageDto;
import org.springframework.data.domain.Pageable;

public interface LabelService {

    LabelDto findById(String id);

    LabelDto save(CreateLabelRq dto);

    PageDto<LabelDto> findAllByPageRequest(String taskId, Pageable pageable);

    LabelDto deleteLabelEntityById(String id);

    LabelDto updateLabelFromController(LabelDto dto);

}
