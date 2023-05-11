package ewp.tasktracker.service.history;

import ewp.tasktracker.api.dto.history.CreateHistoryRq;
import ewp.tasktracker.api.dto.history.HistoryDto;
import ewp.tasktracker.api.dto.history.UpdateHistoryRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.history.HistoryEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional // clean db table
//    clean table "histories" before tests
class HistoryServiceImplTest {
    @Autowired
    HistoryServiceImpl historyService;

    CreateHistoryRq createHistoryRq = new CreateHistoryRq(
            "History1",
            "Description1",
            ProgressStatus.DONE,
            Priority.HIGH,
            "Epic1",
            "Author1",
            "Sprint1"
    );

    @Test
    void save() {
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        assertThat(savedHistory).isNotNull();
        assertThat(savedHistory.getName()).isNotBlank();
        assertThat(savedHistory.getDescription()).isNotBlank();
        assertThat(savedHistory.getStatus()).isNotNull();
        assertThat(savedHistory.getPriority()).isNotNull();
        assertThat(savedHistory.getEpicId()).isNotBlank();
        assertThat(savedHistory.getAuthorId()).isNotBlank();
        assertThat(savedHistory.getSprintId()).isNotBlank();
        assertThat(savedHistory.getCreatedAt().withNano(0)).isEqualTo(LocalDateTime.now().withNano(0));
    }

    @Test
    void findAll() {
        List<CreateHistoryRq> createHistoryRqList = new ArrayList<>();
        createHistoryRqList.add(createHistoryRq);
        createHistoryRqList.add(createHistoryRq);
        createHistoryRqList.add(createHistoryRq);

        for (CreateHistoryRq history : createHistoryRqList) {
            historyService.save(history);
        }

        PageDto<HistoryDto> pageDto = historyService.findAll(0, 3);
        List<HistoryDto> histories = pageDto.getItems();

        assertEquals(histories.size(), createHistoryRqList.size());
        for (int i = 0; i < histories.size(); i++) {
            assertEquals(histories.get(i).getName(), createHistoryRqList.get(i).getName());
            assertEquals(histories.get(i).getDescription(), createHistoryRqList.get(i).getDescription());
            assertEquals(histories.get(i).getStatus(), createHistoryRqList.get(i).getStatus());
            assertEquals(histories.get(i).getPriority(), createHistoryRqList.get(i).getPriority());
            assertEquals(histories.get(i).getEpicId(), createHistoryRqList.get(i).getEpicId());
            assertEquals(histories.get(i).getAuthorId(), createHistoryRqList.get(i).getAuthorId());
            assertEquals(histories.get(i).getSprintId(), createHistoryRqList.get(i).getSprintId());
        }
        assertEquals(pageDto.getItems().size(), 3);
    }

    @Test
    void findById() {
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        String id = savedHistory.getId();
        HistoryEntity foundHistory = historyService.findById(id);
        assertEquals(foundHistory.getId(), savedHistory.getId());

        String exceptionId = "1L";
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy((() -> historyService.findById(exceptionId)))
                .withMessage("History not found, id: 1L");
    }

    @Test
    void findByName() {
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        String name = savedHistory.getName();
        PageDto<HistoryDto> pageDto = historyService.findByName(name,0,1);


        assertThat(pageDto.getItems().get(0).getId()).isEqualTo(savedHistory.getId());
    }

    @Test
    void update() {
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        UpdateHistoryRq updateHistoryRq = new UpdateHistoryRq(
                savedHistory.getId(),
                "History2",
                "Description2",
                ProgressStatus.IN_PROGRESS,
                Priority.LOW
        );
        HistoryEntity updatedHistory = historyService.update(updateHistoryRq);
        assertEquals(updatedHistory.getName(), updateHistoryRq.getName());
        assertEquals(updatedHistory.getDescription(), updateHistoryRq.getDescription());
        assertEquals(updatedHistory.getStatus(), updateHistoryRq.getStatus());
        assertEquals(updatedHistory.getPriority(), updateHistoryRq.getPriority());
        assertEquals(updatedHistory.getUpdatedAt().withNano(0), LocalDateTime.now().withNano(0));
    }
}