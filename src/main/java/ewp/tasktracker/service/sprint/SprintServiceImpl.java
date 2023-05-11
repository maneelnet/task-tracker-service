package ewp.tasktracker.service.sprint;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.sprint.CreateSprintRq;
import ewp.tasktracker.api.dto.sprint.SprintDto;
import ewp.tasktracker.entity.sprint.SprintEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.sprint.SprintRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class SprintServiceImpl implements SprintService {
    private final SprintRepository supersprintRepository;

    @Override
    public SprintDto save(CreateSprintRq dto) {
        return new SprintDto(supersprintRepository.save(dto.toEntity()));
    }

    @Override
    public SprintDto findById(String id) {
        return new SprintDto(supersprintRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Sprint not found, id: " + id)));
    }

    @Override
    public PageDto<SprintDto> findAllByPageRequest(Pageable pageable) {
        Page<SprintEntity> supersprintEntities = supersprintRepository.findAll(pageable);
        return new PageDto<>(supersprintEntities.getContent().stream()
                .map(SprintDto::from).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) supersprintEntities.getTotalElements());
    }

    @Override
    public SprintDto updateSprintFromController(SprintDto supersprintDto) {
        SprintEntity.updateSprint(supersprintRepository.findById(supersprintDto.getId()).get(), supersprintDto);
        return new SprintDto(supersprintRepository.findById(supersprintDto.getId()).get());
    }

    @Override
    public PageDto<SprintDto> findAllByName(String name, Pageable pageable) {
        Page<SprintEntity> supersprintEntities = supersprintRepository.getByName(name, pageable);
        return new PageDto<>(supersprintEntities.getContent().stream()
                .map(SprintDto::from).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) supersprintEntities.getTotalElements());
    }
}
