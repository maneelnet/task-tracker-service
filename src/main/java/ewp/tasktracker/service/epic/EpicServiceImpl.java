package ewp.tasktracker.service.epic;

import ewp.tasktracker.api.dto.epic.CreateEpicRq;
import ewp.tasktracker.api.dto.epic.EpicDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.epic.EpicEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.epic.EpicRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class EpicServiceImpl implements EpicService {
    private final EpicRepository epicRepository;

    @Override
    public EpicDto save(CreateEpicRq dto) {
        return new EpicDto(epicRepository.save(dto.toEntity()));
    }

    @Override
    public EpicDto findById(String id) {
        return new EpicDto(epicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Epic not found, id: " + id)));
    }

    @Override
    public PageDto<EpicDto> findAllByPageRequest(Pageable pageable) {
        Page<EpicEntity> epicEntities = epicRepository.findAll(pageable);
        return new PageDto<>(epicEntities.getContent().stream()
                .map(epicEntity -> EpicDto.from(epicEntity)).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) epicEntities.getTotalElements());
    }

    @Override
    public EpicDto updateEpicFromController(EpicDto dto) {
        EpicEntity.updateEpic(epicRepository.findById(dto.getId()).get(), dto);
        return dto;
    }

    @Override
    public PageDto<EpicDto> findAllByName(String name, Pageable pageable) {
        Page<EpicEntity> epicEntities = epicRepository.getByName(name, pageable);
        return new PageDto<>(epicEntities.getContent().stream()
                .map(epicEntity -> EpicDto.from(epicEntity)).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) epicEntities.getTotalElements());
    }
}
