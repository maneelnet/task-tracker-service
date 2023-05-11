package ewp.tasktracker.service.history;

import ewp.tasktracker.api.dto.history.CreateHistoryRq;
import ewp.tasktracker.api.dto.history.HistoryDto;
import ewp.tasktracker.api.dto.history.UpdateHistoryRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.history.HistoryEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.history.HistoryRepository;
import ewp.tasktracker.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    private final PageUtil pageUtil;

    @Override
    public PageDto<HistoryDto> findAll(int pageNumber, int pageSize) {
        Page<HistoryEntity> historyPage = historyRepository.findAll(pageUtil.buildPageable(pageSize, pageNumber));
        List<HistoryDto> historyDtoList = historyPage.getContent().stream().map(HistoryDto::new).collect(Collectors.toList());
        return new PageDto<>(
                historyDtoList,
                historyPage.getNumber(),
                historyPage.getSize(),
                historyPage.getTotalPages()
        );
    }

    @Override
    public HistoryEntity save(CreateHistoryRq dto) {
        return historyRepository.save(dto.toEntity());
    }

    @Override
    public HistoryEntity findById(String id) {
        return historyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("History not found, id: " + id));
    }

    @Override
    public PageDto<HistoryDto> findByName(String name,int pageNumber,int pageSize) {
        Page<HistoryEntity> historyPage = historyRepository.findByName(name,pageUtil.buildPageable(pageSize,pageNumber));
        List<HistoryDto> historyDtoList = historyPage.getContent().stream().map(HistoryDto::new).collect(Collectors.toList());
        return new PageDto<>(
                historyDtoList,
                historyPage.getNumber(),
                historyPage.getSize(),
                historyPage.getTotalPages()
        );
    }

    @Override
    public HistoryEntity update(UpdateHistoryRq dto) {
        HistoryEntity historyEntity = findById(dto.getId());
        historyEntity.from(dto);
        historyRepository.save(historyEntity);
        return historyEntity;
    }
}
