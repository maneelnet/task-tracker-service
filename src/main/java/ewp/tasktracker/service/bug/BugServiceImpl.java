package ewp.tasktracker.service.bug;

import ewp.tasktracker.api.dto.bug.BugDto;
import ewp.tasktracker.api.dto.bug.CreateBugRq;
import ewp.tasktracker.api.dto.bug.UpdateBugRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.bug.BugEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.bug.BugRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BugServiceImpl implements BugService {

    private final BugRepository bugRepository;

    @Override
    public BugEntity save(CreateBugRq dto) {
        return bugRepository.save(dto.toEntity());
    }

    @Override
    public PageDto<BugDto> findAll(Pageable pageable) {
        Page<BugEntity> bugPage = bugRepository.findAll(pageable);
        List<BugDto> bugDtoList = bugPage.getContent().stream().map(BugDto::new).collect(Collectors.toList());
        return new PageDto<>(bugDtoList, bugPage.getNumber(), bugPage.getSize(), bugPage.getTotalPages());
    }

    @Override
    public PageDto<BugDto> findAllByName(String name, Pageable pageable) {
        Page<BugEntity> bugPage = bugRepository.getByName(name, pageable);
        List<BugDto> bugDtoList = bugPage.getContent().stream().map(BugDto::new).collect(Collectors.toList());
        return new PageDto<>(bugDtoList, bugPage.getNumber(), bugPage.getSize(), bugPage.getTotalPages());
    }

    @Override
    public BugEntity findById(String id) {
        return bugRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bug not found, id: " + id));
    }

    @Override
    public BugEntity update(UpdateBugRq updateBugRq) {
        BugEntity bugEntity = findById(updateBugRq.getId());
        bugEntity.updateBug(updateBugRq);
        bugRepository.save(bugEntity);
        return bugEntity;
    }

    @Override
    public BugEntity deleteById(String id) {
        BugEntity bugEntity = findById(id);
        bugRepository.deleteById(id);
        return bugEntity;
    }

    @Override
    public PageDto<BugDto> findAllByAssigneeId(String assigneeId, Pageable pageable) {
        Page<BugEntity> bugPage = bugRepository.getByAssigneeId(assigneeId, pageable);
        List<BugDto> bugDtoList = bugPage.getContent().stream().map(BugDto::new).collect(Collectors.toList());
        return new PageDto<>(bugDtoList, bugPage.getNumber(), bugPage.getSize(), bugPage.getTotalPages());
    }

    @Override
    public PageDto<BugDto> findAllByProjectIdAndDate(String projectId, LocalDateTime createdDate, Pageable pageable) {
        Page<BugEntity> bugPage = bugRepository.getByProjectIdAndDate(projectId, createdDate, pageable);
        List<BugDto> bugDtoList = bugPage.getContent().stream().map(BugDto::new).collect(Collectors.toList());
        return new PageDto<>(bugDtoList, bugPage.getNumber(), bugPage.getSize(), bugPage.getTotalPages());
    }

}
