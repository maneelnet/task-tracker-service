package ewp.tasktracker.service.workload;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.workload.CreateWorkloadRq;
import ewp.tasktracker.api.dto.workload.UpdateWorkloadRq;
import ewp.tasktracker.api.dto.workload.WorkloadDto;
import ewp.tasktracker.entity.workload.WorkloadEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.workload.WorkloadRepository;
import ewp.tasktracker.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WorkloadServiceImpl implements WorkloadService {

    private final WorkloadRepository workloadRepository;
    private final PageUtil pageUtil;

    @Override
    public WorkloadEntity save(CreateWorkloadRq dto) { return workloadRepository.save(dto.toEntity());
    }

    @Override
    public WorkloadEntity findById(String id) {
        return workloadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workload not found with id: " + id));
    }

    @Override
    public PageDto<WorkloadDto> findAll(Integer pageSize, Integer pageNumber) {
        Page<WorkloadEntity> workloadPage = workloadRepository.findAll(pageUtil.buildPageable(pageSize, pageNumber));
        List<WorkloadDto> workloadDtoList = workloadPage.getContent()
                .stream()
                .map(WorkloadDto::new)
                .collect(Collectors.toList());
        return new PageDto<>(
                workloadDtoList,
                workloadPage.getNumber(),
                workloadPage.getSize(),
                workloadPage.getTotalPages()
        );
    }

    @Override
    public WorkloadEntity update(UpdateWorkloadRq dto) {
        WorkloadEntity existingWorkload = findById(dto.getId());
        existingWorkload.updateEntity(dto);
        return workloadRepository.save(existingWorkload);
    }
}