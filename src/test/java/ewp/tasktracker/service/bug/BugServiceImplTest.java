package ewp.tasktracker.service.bug;

import ewp.tasktracker.api.dto.bug.BugDto;
import ewp.tasktracker.api.dto.bug.CreateBugRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.bug.BugEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.bug.BugRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BugServiceImplTest {

    @Mock
    private BugRepository bugRepository;
    @InjectMocks
    private BugServiceImpl bugService;


    @Test
    void should_save_one_bug() {
        CreateBugRq createBugRq = new CreateBugRq();

        createBugRq.setName("some name");
        createBugRq.setStatus(ProgressStatus.DONE);
        createBugRq.setPriority(Priority.LOW);
        createBugRq.setHistoryId("Some history");
        createBugRq.setDescription("unit test");
        createBugRq.setAssigneeId("Some assignee");
        createBugRq.setAuthorId("Some author");

        when(bugRepository.save(any(BugEntity.class))).thenReturn(createBugRq.toEntity());

        BugEntity bugEntity = bugService.save(createBugRq);
        Assert.notNull(new BugDto(bugEntity));
        verify(bugRepository, times(1)).save(any(BugEntity.class));
    }

    @Test
    void should_find_all_bugs() {
        Page<BugEntity> page = new PageImpl<>(List.of(new BugEntity(), new BugEntity()), PageRequest.of(0, 20), 2);
        when(bugRepository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        PageDto<BugDto> pageDto = bugService.findAll(PageRequest.of(0, 20));

        assertThat(pageDto.getItems().size()).isEqualTo(page.getTotalElements());
        verify(bugRepository, times(1)).findAll(PageRequest.of(0, 20));
    }

    @Test
    void should_find_bug_by_name() {
        BugEntity bugEntity = new BugEntity();
        bugEntity.setName("some name");

        Page<BugEntity> page = new PageImpl<>(List.of(bugEntity), PageRequest.of(0, 20), 2);
        when(bugRepository.getByName("some name",PageRequest.of(0, 20))).thenReturn(page);
        PageDto<BugDto> pageDto = bugService.findAllByName("some name", PageRequest.of(0, 20));

        assertThat(pageDto.getItems().get(0).getName()).isEqualTo(page.getContent().get(0).getName());
        verify(bugRepository, times(1)).getByName("some name",PageRequest.of(0, 20));
    }

    @Test
    void should_find_bug_by_id() {
        BugEntity bugEntity = new BugEntity();

        bugEntity.setName("some name");
        bugEntity.setStatus(ProgressStatus.DONE);
        bugEntity.setPriority(Priority.LOW);
        bugEntity.setHistoryId("Some history");
        bugEntity.setDescription("unit test");
        bugEntity.setAssigneeId("Some assignee");
        bugEntity.setAuthorId("Some author");

        when(bugRepository.findById(anyString())).thenReturn(Optional.of(bugEntity));
        Optional<BugEntity> actualBug = bugRepository.findById(UUID.randomUUID().toString());

        assertThat(actualBug.get()).usingRecursiveComparison().isEqualTo(bugEntity);
        verify(bugRepository, times(1)).findById(anyString());
    }

    @Test
    void should_not_find_by_id() {
        when(bugRepository.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bugService.findById(UUID.randomUUID().toString()));
        verify(bugRepository, times(1)).findById(anyString());
    }

    @Test
    void should_update_bug() {
        CreateBugRq createBugRq = new CreateBugRq();
        UpdateBugRq updateBugRq = new UpdateBugRq();

        createBugRq.setName("some name");
        createBugRq.setDescription("some desc");

        when(bugRepository.save(any(BugEntity.class))).thenReturn(createBugRq.toEntity());

        BugEntity createdEntity = bugService.save(createBugRq);

        when(bugRepository.findById(anyString())).thenReturn(Optional.of(createdEntity));

        updateBugRq.setId(createdEntity.getId());
        updateBugRq.setName("Ramil");
        updateBugRq.setDescription("unit test");

        BugEntity updatedEntity = bugService.update(updateBugRq);

        assertThat(updatedEntity.getId()).isEqualTo(createdEntity.getId());
        assertThat(updatedEntity.getName()).isEqualTo("Ramil");
        assertThat(updatedEntity.getDescription()).isEqualTo("unit test");

        verify(bugRepository, times(2)).save(any(BugEntity.class));
    }

    @Test
    void should_delete_bug_by_id() {
        BugEntity bugEntity = new BugEntity();
        when(bugRepository.findById(anyString())).thenReturn(Optional.of(bugEntity));
        doNothing().when(bugRepository).deleteById(anyString());

        BugEntity deletedBugEntity = bugService.deleteById(bugEntity.getId());
        assertThat(bugEntity).isEqualTo(deletedBugEntity);

        verify(bugRepository, times(1)).deleteById(anyString());
    }
}