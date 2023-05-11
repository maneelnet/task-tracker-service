package ewp.tasktracker.service.sprint;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.sprint.CreateSprintRq;
import ewp.tasktracker.api.dto.sprint.SprintDto;
import ewp.tasktracker.entity.sprint.SprintEntity;
import ewp.tasktracker.repository.sprint.SprintRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SprintServiceImplTest {

    @Mock
    private SprintRepository sprintRepository;
    @InjectMocks
    private SprintServiceImpl service;

    @Test
    void save() {
        CreateSprintRq createSprintRq = new CreateSprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId");
        Mockito.when(sprintRepository.save(any(SprintEntity.class))).thenReturn(createSprintRq.toEntity());
        SprintDto sprintDto = service.save(createSprintRq);
        verify(sprintRepository, times(1)).save(any(SprintEntity.class));
        Assertions.assertNotEquals(sprintDto, new SprintDto());
    }

    @Test
    void findById() {
        SprintEntity sprintEntity = new SprintEntity("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId");
        when(sprintRepository.findById(anyString())).thenReturn(Optional.of(sprintEntity));
        Optional<SprintEntity> actualBug = sprintRepository.findById(UUID.randomUUID().toString());

        assertThat(actualBug.get()).usingRecursiveComparison().isEqualTo(sprintEntity);
        verify(sprintRepository, times(1)).findById(anyString());
    }

    @Test
    void findAllByPageRequest() {
        Page<SprintEntity> page = new PageImpl<>(List.of(new SprintEntity(), new SprintEntity()), PageRequest.of(0, 20), 2);
        when(sprintRepository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        PageDto<SprintDto> pageDto = service.findAllByPageRequest(PageRequest.of(0, 20));
        assertThat(pageDto.getItems().size()).isEqualTo(page.getTotalElements());
        verify(sprintRepository, times(1)).findAll(PageRequest.of(0, 20));
    }

    @Test
    void updateSprintFromController() {
        Mockito.when(sprintRepository.save(Mockito.any(SprintEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        SprintDto sprintDtoForUpdate1 = service.save(new CreateSprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId"));
        SprintDto sprintDtoForUpdate2 = service.save(new CreateSprintRq("name2",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId2", "supersprintId2"));
        Mockito.when(sprintRepository.findById(sprintDtoForUpdate1.getId())).thenReturn(Optional.of
                (sprintDtoForUpdate1.toEntity()));
        Mockito.when(sprintRepository.findById(sprintDtoForUpdate2.getId())).thenReturn(Optional.of
                (sprintDtoForUpdate2.toEntity()));
        SprintDto updateDSprintDto1 = service.updateSprintFromController(sprintDtoForUpdate1);
        updateDSprintDto1.setId(sprintDtoForUpdate1.getId());
        updateDSprintDto1.setCreatedAt(sprintDtoForUpdate1.getCreatedAt());
        updateDSprintDto1.setUpdatedAt(sprintDtoForUpdate1.getUpdatedAt());
        SprintDto updateDSprintDto2 = service.updateSprintFromController(sprintDtoForUpdate2);
        updateDSprintDto2.setId(sprintDtoForUpdate2.getId());
        updateDSprintDto2.setCreatedAt(sprintDtoForUpdate2.getCreatedAt());
        updateDSprintDto2.setUpdatedAt(sprintDtoForUpdate2.getUpdatedAt());
        Assertions.assertNotNull(updateDSprintDto1);
        Assertions.assertNotNull(updateDSprintDto2);
        Assertions.assertNotEquals(updateDSprintDto1, updateDSprintDto2);
        Assertions.assertNotEquals(sprintDtoForUpdate1, sprintDtoForUpdate2);
        Assertions.assertEquals(updateDSprintDto1, sprintDtoForUpdate1);
        Assertions.assertEquals(updateDSprintDto2, sprintDtoForUpdate2);
    }

    @Test
    void findAllByName() {
        Mockito.when(sprintRepository.save(Mockito.any(SprintEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        SprintDto sprintDto1 = service.save(new CreateSprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId"));
        SprintDto sprintDto2 = service.save(new CreateSprintRq("name2",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId2", "supersprintId2"));
        Assertions.assertNotNull(sprintDto1);
        Assertions.assertNotNull(sprintDto2);
        Assertions.assertNotEquals(sprintDto1, sprintDto2);
        Mockito.when(sprintRepository.findById(sprintDto1.getId())).thenReturn(Optional.of
                (sprintDto1.toEntity()));
        Mockito.when(sprintRepository.findById(sprintDto2.getId())).thenReturn(Optional.of
                (sprintDto2.toEntity()));
        SprintDto sprintDtoForUpdate1 = service.findById(sprintDto1.getId());
        sprintDtoForUpdate1.setId(sprintDto1.getId());
        sprintDtoForUpdate1.setCreatedAt(sprintDto1.getCreatedAt());
        sprintDtoForUpdate1.setUpdatedAt(sprintDto1.getUpdatedAt());
        SprintDto sprintDtoForUpdate2 = service.findById(sprintDto2.getId());
        sprintDtoForUpdate2.setId(sprintDto2.getId());
        sprintDtoForUpdate2.setCreatedAt(sprintDto2.getCreatedAt());
        sprintDtoForUpdate2.setUpdatedAt(sprintDto2.getUpdatedAt());
        Mockito.when(sprintRepository.getByName(sprintDto1.getName(), PageRequest.of(0, 20))).thenThrow(ArithmeticException.class);
        Mockito.when(sprintRepository.getByName(sprintDto2.getName(), PageRequest.of(2, 5))).thenThrow(ClassCastException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> service.findAllByName("name", PageRequest.of(0, 20)));
        Assertions.assertThrows(ClassCastException.class, () -> service.findAllByName("name2", PageRequest.of(2, 5)));
    }
}