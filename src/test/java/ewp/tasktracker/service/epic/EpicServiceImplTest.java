package ewp.tasktracker.service.epic;

import ewp.tasktracker.api.dto.epic.CreateEpicRq;
import ewp.tasktracker.api.dto.epic.EpicDto;
import ewp.tasktracker.entity.epic.EpicEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.repository.epic.EpicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EpicServiceImplTest {

    @Mock
    private EpicRepository epicRepository;

    @InjectMocks
    private EpicServiceImpl epicService;

    @BeforeEach
    private void mocker() {
        Mockito.when(epicRepository.save(Mockito.any(EpicEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void save() {
        CreateEpicRq epicRq1 = new CreateEpicRq("name", "description"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId");
        CreateEpicRq epicRq2 = new CreateEpicRq("name2", "description2"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2");
        EpicDto epicDto1 = epicService.save(epicRq1);
        EpicDto epicDto2 = epicService.save(epicRq2);
        Assertions.assertNotNull(epicDto1); Assertions.assertNotNull(epicDto2);
        Assertions.assertNotEquals(epicDto1, epicDto2);
    }

    @Test
    void findById() {
        EpicDto epicDto1 = epicService.save(new CreateEpicRq("name", "description"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId"));
        EpicDto epicDto2 = epicService.save(new CreateEpicRq("name2", "description2"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2"));
        Assertions.assertNotNull(epicDto1); Assertions.assertNotNull(epicDto2);
        Assertions.assertNotEquals(epicDto1, epicDto2);
        Mockito.when(epicRepository.findById(epicDto1.getId())).thenReturn(Optional.of
                (new EpicEntity("name", "description"
                        , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId")));
        Mockito.when(epicRepository.findById(epicDto2.getId())).thenReturn(Optional.of
                (new EpicEntity("name2", "description2"
                        , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2")));
        EpicDto dto1 = epicService.findById(epicDto1.getId());
        dto1.setId(epicDto1.getId());
        dto1.setCreatedAt(epicDto1.getCreatedAt());
        dto1.setUpdatedAt(epicDto1.getUpdatedAt());
        EpicDto dto2 = epicService.findById(epicDto2.getId());
        dto2.setId(epicDto2.getId());
        dto2.setCreatedAt(epicDto2.getCreatedAt());
        dto2.setUpdatedAt(epicDto2.getUpdatedAt());
        Assertions.assertNotNull(dto1); Assertions.assertNotNull(dto2);
        Assertions.assertNotEquals(dto2, epicDto1); Assertions.assertNotEquals(dto1, epicDto2);
        Assertions.assertEquals(dto1, epicDto1); Assertions.assertEquals(dto2, epicDto2);
    }

    @Test
    void findAllByPageRequest() {
        Mockito.when(epicRepository.findAll(PageRequest.of(0, 20))).thenThrow(ArithmeticException.class);
        Mockito.when(epicRepository.findAll(PageRequest.of(2, 5))).thenThrow(ClassCastException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> epicService.findAllByPageRequest(PageRequest.of(0, 20)));
        Assertions.assertThrows(ClassCastException.class, () -> epicService.findAllByPageRequest(PageRequest.of(2, 5)));
    }

    @Test
    void updateEpicFromController() {
        EpicDto epicDto1 = epicService.save(new CreateEpicRq("name", "description"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId"));
        EpicDto epicDto2 = epicService.save(new CreateEpicRq("name2", "description2"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2"));
        Assertions.assertNotNull(epicDto1); Assertions.assertNotNull(epicDto2);
        Assertions.assertNotEquals(epicDto1, epicDto2);
        Mockito.when(epicRepository.findById(epicDto1.getId())).thenReturn(Optional.of
                (new EpicEntity("name", "description"
                        , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId")));
        Mockito.when(epicRepository.findById(epicDto2.getId())).thenReturn(Optional.of
                (new EpicEntity("name2", "description2"
                        , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2")));
        EpicDto epicDtoForUpdate1 = epicService.findById(epicDto1.getId());
        epicDtoForUpdate1.setId(epicDto1.getId());
        epicDtoForUpdate1.setCreatedAt(epicDto1.getCreatedAt());
        epicDtoForUpdate1.setUpdatedAt(epicDto1.getUpdatedAt());
        EpicDto epicDtoForUpdate2 = epicService.findById(epicDto2.getId());
        epicDtoForUpdate2.setId(epicDto2.getId());
        epicDtoForUpdate2.setCreatedAt(epicDto2.getCreatedAt());
        epicDtoForUpdate2.setUpdatedAt(epicDto2.getUpdatedAt());
        EpicDto updateDEpicDto1 = epicService.updateEpicFromController(epicDtoForUpdate1);
        EpicDto updateDEpicDto2 = epicService.updateEpicFromController(epicDtoForUpdate2);
        Assertions.assertNotNull(updateDEpicDto1); Assertions.assertNotNull(updateDEpicDto2);
        Assertions.assertNotEquals(updateDEpicDto1, updateDEpicDto2); Assertions.assertNotEquals(epicDtoForUpdate1, epicDtoForUpdate2);
        Assertions.assertEquals(updateDEpicDto1, epicDtoForUpdate1); Assertions.assertEquals(updateDEpicDto2, epicDtoForUpdate2);
    }

    @Test
    void findAllByName() {
        EpicDto epicDto1 = epicService.save(new CreateEpicRq("name", "description"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId"));
        EpicDto epicDto2 = epicService.save(new CreateEpicRq("name2", "description2"
                , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2"));
        Assertions.assertNotNull(epicDto1); Assertions.assertNotNull(epicDto2);
        Assertions.assertNotEquals(epicDto1, epicDto2);
        Mockito.when(epicRepository.findById(epicDto1.getId())).thenReturn(Optional.of
                (new EpicEntity("name", "description"
                        , ProgressStatus.TODO, Priority.MEDIUM, "projectId", "authorId", "supersprintId")));
        Mockito.when(epicRepository.findById(epicDto2.getId())).thenReturn(Optional.of
                (new EpicEntity("name2", "description2"
                        , ProgressStatus.TODO, Priority.MEDIUM, "projectId2", "authorId2", "supersprintId2")));
        EpicDto epicDtoForUpdate1 = epicService.findById(epicDto1.getId());
        epicDtoForUpdate1.setId(epicDto1.getId());
        epicDtoForUpdate1.setCreatedAt(epicDto1.getCreatedAt());
        epicDtoForUpdate1.setUpdatedAt(epicDto1.getUpdatedAt());
        EpicDto epicDtoForUpdate2 = epicService.findById(epicDto2.getId());
        epicDtoForUpdate2.setId(epicDto2.getId());
        epicDtoForUpdate2.setCreatedAt(epicDto2.getCreatedAt());
        epicDtoForUpdate2.setUpdatedAt(epicDto2.getUpdatedAt());
        Mockito.when(epicRepository.getByName(epicDto1.getName(), PageRequest.of(0, 20))).thenThrow(ArithmeticException.class);
        Mockito.when(epicRepository.getByName(epicDto2.getName(), PageRequest.of(2, 5))).thenThrow(ClassCastException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> epicService.findAllByName("name", PageRequest.of(0, 20)));
        Assertions.assertThrows(ClassCastException.class, () -> epicService.findAllByName("name2", PageRequest.of(2, 5)));
    }
}