package ewp.tasktracker.service.supersprint;

import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.supersprint.CreateSupersprintRq;
import ewp.tasktracker.api.dto.supersprint.SupersprintDto;
import ewp.tasktracker.entity.supersprint.SupersprintEntity;
import ewp.tasktracker.repository.supersprint.SupersprintRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SupersprintServiceImplTest {

    @Mock
    private SupersprintRepository supersprintRepository;
    @InjectMocks
    private SupersprintServiceImpl service;

    @Test
    void save() {
        CreateSupersprintRq createSprintRq = new CreateSupersprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId");
        Mockito.when(supersprintRepository.save(any(SupersprintEntity.class))).thenReturn(createSprintRq.toEntity());
        SupersprintDto sprintDto = service.save(createSprintRq);
        verify(supersprintRepository, times(1)).save(any(SupersprintEntity.class));
        Assertions.assertNotEquals(sprintDto, new SupersprintDto());
    }

    @Test
    void findById() {
        SupersprintEntity sprintEntity = new SupersprintEntity("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId");
        when(supersprintRepository.findById(anyString())).thenReturn(Optional.of(sprintEntity));
        Optional<SupersprintEntity> actualBug = supersprintRepository.findById(UUID.randomUUID().toString());

        assertThat(actualBug.get()).usingRecursiveComparison().isEqualTo(sprintEntity);
        verify(supersprintRepository, times(1)).findById(anyString());
    }

    @Test
    void findAllByPageRequest() {
        Page<SupersprintEntity> page = new PageImpl<>(List.of(new SupersprintEntity(), new SupersprintEntity()), PageRequest.of(0, 20), 2);
        when(supersprintRepository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        PageDto<SupersprintDto> pageDto = service.findAllByPageRequest(PageRequest.of(0, 20));
        assertThat(pageDto.getItems().size()).isEqualTo(page.getTotalElements());
        verify(supersprintRepository, times(1)).findAll(PageRequest.of(0, 20));
    }

    @Test
    void updateSprintFromController() {
        Mockito.when(supersprintRepository.save(Mockito.any(SupersprintEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        SupersprintDto sprintDtoForUpdate1 = service.save(new CreateSupersprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId"));
        SupersprintDto sprintDtoForUpdate2 = service.save(new CreateSupersprintRq("name2",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId2"));
        Mockito.when(supersprintRepository.findById(sprintDtoForUpdate1.getId())).thenReturn(Optional.of
                (sprintDtoForUpdate1.toEntity()));
        Mockito.when(supersprintRepository.findById(sprintDtoForUpdate2.getId())).thenReturn(Optional.of
                (sprintDtoForUpdate2.toEntity()));
        SupersprintDto updateDSprintDto1 = service.updateSupersprintFromController(sprintDtoForUpdate1);
        updateDSprintDto1.setId(sprintDtoForUpdate1.getId());
        updateDSprintDto1.setCreatedAt(sprintDtoForUpdate1.getCreatedAt());
        updateDSprintDto1.setUpdatedAt(sprintDtoForUpdate1.getUpdatedAt());
        SupersprintDto updateDSprintDto2 = service.updateSupersprintFromController(sprintDtoForUpdate2);
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
        Mockito.when(supersprintRepository.save(Mockito.any(SupersprintEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        SupersprintDto sprintDto1 = service.save(new CreateSupersprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId"));
        SupersprintDto sprintDto2 = service.save(new CreateSupersprintRq("name2",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId2"));
        Assertions.assertNotNull(sprintDto1);
        Assertions.assertNotNull(sprintDto2);
        Assertions.assertNotEquals(sprintDto1, sprintDto2);
        Mockito.when(supersprintRepository.findById(sprintDto1.getId())).thenReturn(Optional.of
                (sprintDto1.toEntity()));
        Mockito.when(supersprintRepository.findById(sprintDto2.getId())).thenReturn(Optional.of
                (sprintDto2.toEntity()));
        SupersprintDto sprintDtoForUpdate1 = service.findById(sprintDto1.getId());
        sprintDtoForUpdate1.setId(sprintDto1.getId());
        sprintDtoForUpdate1.setCreatedAt(sprintDto1.getCreatedAt());
        sprintDtoForUpdate1.setUpdatedAt(sprintDto1.getUpdatedAt());
        SupersprintDto sprintDtoForUpdate2 = service.findById(sprintDto2.getId());
        sprintDtoForUpdate2.setId(sprintDto2.getId());
        sprintDtoForUpdate2.setCreatedAt(sprintDto2.getCreatedAt());
        sprintDtoForUpdate2.setUpdatedAt(sprintDto2.getUpdatedAt());
        Mockito.when(supersprintRepository.getByName(sprintDto1.getName(), PageRequest.of(0, 20))).thenThrow(ArithmeticException.class);
        Mockito.when(supersprintRepository.getByName(sprintDto2.getName(), PageRequest.of(2, 5))).thenThrow(ClassCastException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> service.findAllByName("name", PageRequest.of(0, 20)));
        Assertions.assertThrows(ClassCastException.class, () -> service.findAllByName("name2", PageRequest.of(2, 5)));
    }
}