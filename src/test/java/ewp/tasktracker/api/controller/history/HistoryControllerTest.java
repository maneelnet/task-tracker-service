package ewp.tasktracker.api.controller.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.history.CreateHistoryRq;
import ewp.tasktracker.api.dto.history.HistoryDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.history.UpdateHistoryRq;
import ewp.tasktracker.entity.history.HistoryEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.service.history.HistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class HistoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private HistoryServiceImpl historyService;

    @Autowired
    ObjectMapper objectMapper;

    public static CreateHistoryRq getCreateHistoryRq() {
        return new CreateHistoryRq(
                "Name1",
                "Description1",
                ProgressStatus.DONE,
                Priority.HIGH,
                "Epic1",
                "Author1",
                "Sprint1"
        );
    }

    @Test
    void save() throws Exception {

        CreateHistoryRq createHistoryRq = getCreateHistoryRq();

        this.mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createHistoryRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createHistoryRq.getName()))
                .andExpect(jsonPath("$.description").value(createHistoryRq.getDescription()));
    }

    @Test
    void shouldNotSaveValidationException() throws Exception {

        CreateHistoryRq createHistoryRq = new CreateHistoryRq(
                "",
                "",
                ProgressStatus.DONE,
                Priority.HIGH,
                "",
                "",
                "");

        this.mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createHistoryRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update() throws Exception {

        CreateHistoryRq createHistoryRq = getCreateHistoryRq();
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        UpdateHistoryRq updateHistoryRq = new UpdateHistoryRq(
                savedHistory.getId(),
                "Name2",
                "Description2",
                ProgressStatus.DONE,
                Priority.HIGH
        );

        this.mockMvc.perform(put("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHistoryRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateHistoryRq.getName()))
                .andExpect(jsonPath("$.description").value(updateHistoryRq.getDescription()));
    }

    @Test
    void getAll() throws Exception {

        PageDto<HistoryDto> pageDto = historyService.findAll(0, 20);

        this.mockMvc.perform(get("/api/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(pageDto.getItems().size()));
    }

    @Test
    void findById() throws Exception {

        CreateHistoryRq createHistoryRq = getCreateHistoryRq();
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        String id = savedHistory.getId();

        this.mockMvc.perform(get("/api/history/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(id))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(savedHistory.getName()));
    }

    @Test
    void shouldNotFindById() throws Exception {

        String wrongId = "111";

        this.mockMvc.perform(get("/api/history/{id}", wrongId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongId))
                        .characterEncoding("utf-8"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findByName() throws Exception {

        CreateHistoryRq createHistoryRq = getCreateHistoryRq();
        HistoryEntity savedHistory = historyService.save(createHistoryRq);
        String name = savedHistory.getName();
        PageDto<HistoryDto> pageDto = historyService.findByName(name, 0, 20);

        this.mockMvc.perform(get("/api/history/filter")
                        .param("name", name)
                        .param("pageNumber", "0")
                        .param("pageSize", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(pageDto.getItems().size()));
    }
}