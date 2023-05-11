package ewp.tasktracker.api.controller.workload;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.workload.CreateWorkloadRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.workload.UpdateWorkloadRq;
import ewp.tasktracker.api.dto.workload.WorkloadDto;
import ewp.tasktracker.entity.workload.WorkloadEntity;
import ewp.tasktracker.enums.ActivityStatus;
import ewp.tasktracker.service.workload.WorkloadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WorkloadControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WorkloadService workloadService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save() throws Exception {
        CreateWorkloadRq dto = createWorkload();

        mockMvc.perform(post("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.status", is(dto.getStatus().toString())))
                .andExpect(jsonPath("$.authorId", is(dto.getAuthorId())));
    }

    @Test
    void doNotSaveInvalidName() throws Exception {
        CreateWorkloadRq dto = createWorkload();

        dto.setName(""); // @Size(min = 2, max = 128)

        mockMvc.perform(post("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());

        dto.setName(null); // @NotNull

        mockMvc.perform(post("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void doNotSaveInvalidAuthorId() throws Exception {
        CreateWorkloadRq dto = createWorkload();

        dto.setAuthorId("more-36-simbol-text-more-36-simbol-text"); // @Size(max = 36)

        mockMvc.perform(post("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());

        dto.setAuthorId(null); // @NotNull

        mockMvc.perform(post("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getById() throws Exception {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity workloadEntity = workloadService.save(dto);

        mockMvc.perform(get("/api/workload/{id}", workloadEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workloadEntity.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.status", is(dto.getStatus().toString())))
                .andExpect(jsonPath("$.authorId", is(dto.getAuthorId())));
    }

    @Test
    void shouldNotGetById() throws Exception {
        mockMvc.perform(get("/api/workload/{id}", "wrong-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("wrong-id")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        PageDto<WorkloadDto> pageDto = workloadService.findAll(20, 0);

        mockMvc.perform(get("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.items.size()", is(pageDto.getItems().size())),
                        jsonPath("$.pageNumber", is(pageDto.getPageNumber())),
                        jsonPath("$.pageSize", is(pageDto.getPageSize())),
                        jsonPath("$.total", is(pageDto.getTotal()))
                );
    }

    @Test
    void update() throws Exception {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        UpdateWorkloadRq updateDto = updateWorkload(savedWorkload.getId(), dto);

        mockMvc.perform(put("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", is(updateDto.getId())),
                        jsonPath("$.name", is(updateDto.getName())),
                        jsonPath("$.status", is(updateDto.getStatus().toString())),
                        jsonPath("$.authorId", is(updateDto.getAuthorId()))
                );
    }

    @Test
    void doNotUpdateInvalidName() throws Exception {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        UpdateWorkloadRq updateDto = updateWorkload(savedWorkload.getId(), dto);

        updateDto.setName(""); // @Size(min = 2, max = 128)

        mockMvc.perform(put("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().is4xxClientError());

        updateDto.setName(null); // @NotNull

        mockMvc.perform(put("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void doNotUpdateInvalidAuthorId() throws Exception {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        UpdateWorkloadRq updateDto = updateWorkload(savedWorkload.getId(), dto);

        updateDto.setAuthorId("more-36-simbol-text-more-36-simbol-text"); // @Size(max = 36)

        mockMvc.perform(put("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().is4xxClientError());

        updateDto.setAuthorId(null); // @NotNull

        mockMvc.perform(put("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void notEntityForUpdate() throws Exception {
        CreateWorkloadRq dto = createWorkload();
        WorkloadEntity savedWorkload = workloadService.save(dto);
        UpdateWorkloadRq updateDto = updateWorkload(savedWorkload.getId(), dto);

        updateDto.setId("non-existent-id");

        mockMvc.perform(put("/api/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().is4xxClientError());
    }

    private static CreateWorkloadRq createWorkload() {
        List<CreateWorkloadRq> list = new ArrayList<>();
        list.add(new CreateWorkloadRq("Test name 1", ActivityStatus.INACTIVE, "Test authorId 1"));
        list.add(new CreateWorkloadRq("Test name 2", ActivityStatus.INACTIVE, "Test authorId 2"));
        list.add(new CreateWorkloadRq("Test name 3", ActivityStatus.INACTIVE, "Test authorId 3"));
        list.add(new CreateWorkloadRq("Test name 4", ActivityStatus.INACTIVE, "Test authorId 4"));

        Random random = new Random();
        return list.get(random.nextInt(4));
    }

    private static UpdateWorkloadRq updateWorkload(String id, CreateWorkloadRq dto) {
        dto.setName("Test name Updated");
        dto.setStatus(ActivityStatus.ACTIVE);
        dto.setAuthorId("Test author Updated");
        return new UpdateWorkloadRq(id, dto.getName(), dto.getStatus(), dto.getAuthorId());
    }
}