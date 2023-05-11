package ewp.tasktracker.service.supersprint;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.supersprint.CreateSupersprintRq;
import ewp.tasktracker.api.dto.supersprint.SupersprintDto;
import ewp.tasktracker.entity.supersprint.SupersprintEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.supersprint.SupersprintRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class SupersprintServiceImpl implements SupersprintService {
    private final SupersprintRepository supersprintRepository;

    @Override
    public SupersprintDto save(CreateSupersprintRq dto) {
        return new SupersprintDto(supersprintRepository.save(dto.toEntity()));
    }

    @Override
    public SupersprintDto findById(String id) {
        return new SupersprintDto(supersprintRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Supersprint not found, id: " + id)));
    }

    @Override
    public PageDto<SupersprintDto> findAllByPageRequest(Pageable pageable) {
        Page<SupersprintEntity> supersprintEntities = supersprintRepository.findAll(pageable);
        return new PageDto<>(supersprintEntities.getContent().stream()
                .map(SupersprintDto::from).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) supersprintEntities.getTotalElements());
    }

    @Override
    public SupersprintDto updateSupersprintFromController(SupersprintDto supersprintDto) {
        SupersprintEntity.updateSupersprint(supersprintRepository.findById(supersprintDto.getId()).get(), supersprintDto);
        return new SupersprintDto(supersprintRepository.findById(supersprintDto.getId()).get());
    }

    @Override
    public PageDto<SupersprintDto> findAllByName(String name, Pageable pageable) {
        Page<SupersprintEntity> supersprintEntities = supersprintRepository.getByName(name, pageable);
        return new PageDto<>(supersprintEntities.getContent().stream()
                .map(SupersprintDto::from).collect(Collectors.toList()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) supersprintEntities.getTotalElements());
    }
}
