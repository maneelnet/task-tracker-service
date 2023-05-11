package ewp.tasktracker.api.controller.epic;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.epic.CreateEpicRq;
import ewp.tasktracker.api.dto.epic.EpicDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.service.epic.EpicServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EpicControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EpicServiceImpl epicService;


    @Test
    void save() throws Exception {

        this.mockMvc.perform(post("/api/epic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateEpicRq("name", "description", ProgressStatus.DONE, Priority.MEDIUM,
                                        "projectId", "authorId", "supersprintId"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is("name")))
                .andExpect(jsonPath("$.description",
                        is("description")))
                .andExpect(jsonPath("$.status",
                        is("DONE")))
                .andExpect(jsonPath("$.priority",
                        is("MEDIUM")))
                .andExpect(jsonPath("$.projectId",
                        is("projectId")))
                .andExpect(jsonPath("$.authorId",
                        is("authorId")))
                .andExpect(jsonPath("$.supersprintId",
                        is("supersprintId")));
        this.mockMvc.perform(post("/api/epic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateEpicRq("name", "description", ProgressStatus.DONE, Priority.MEDIUM,
                                        "projectId",
                                        "authorId",
                                        "1234567890123456789012345678901234567hereManyTo36Symbols"))))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void getAll() throws Exception {
        PageDto<EpicDto> page = epicService.findAllByPageRequest(PageRequest.of(3, 24));
        this.mockMvc.perform(get("/api/label")
                        .param("pageNumber", String.valueOf(page.getPageNumber()))
                        .param("pageSize", String.valueOf(page.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()", is(page.getItems().size())))
                .andExpect(jsonPath("$.pageNumber", is(page.getPageNumber())))
                .andExpect(jsonPath("$.pageSize", is(page.getPageSize())));
    }
    @Test
    void updateById() throws Exception {

        EpicDto epicDto = epicService.save(new CreateEpicRq("nameEpicForUpdateById", "descriptionEpicForUpdateById", ProgressStatus.DONE,
                Priority.MEDIUM, "projectIdEpicForUpdateById", "authorIdEpicForUpdateById", "superSprintIdEpicForUpdateById"));
        epicDto.setName("newName");
        epicDto.setDescription("newDesc");
        epicDto.setProjectId("newProjectId");
        epicDto.setAuthorId("newAuthorId");
        epicDto.setSupersprintId("newSupersprintId");
        epicDto.setPriority(Priority.LOW);
        epicDto.setStatus(ProgressStatus.IN_PROGRESS);
        this.mockMvc.perform(put("/api/epic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(epicDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        is(epicDto.getId())))
                .andExpect(jsonPath("$.name",
                        is(epicDto.getName())))
                .andExpect(jsonPath("$.description",
                        is(epicDto.getDescription())))
                .andExpect(jsonPath("$.status",
                        not("TODO")))
                .andExpect(jsonPath("$.priority",
                        not("HIGH")))
                .andExpect(jsonPath("$.projectId",
                        is(epicDto.getProjectId())))
                .andExpect(jsonPath("$.authorId",
                        is(epicDto.getAuthorId())))
                .andExpect(jsonPath("$.supersprintId",
                        is(epicDto.getSupersprintId())));
        epicDto.setName("newName");
        epicDto.setDescription("newDesc");
        epicDto.setProjectId("newProjectIdnewProjectIdnewProjectIdnewProjectIdnewProjectIdnewProjectIdnewProjectIdnewProjectId");
        epicDto.setAuthorId("newAuthorIdnewAuthorIdnewAuthorIdnewAuthorIdnewAuthorIdnewAuthorIdnewAuthorIdnewAuthorId");
        epicDto.setSupersprintId("newSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintId");
        this.mockMvc.perform(put("/api/epic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(epicDto)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void getAllByName() throws Exception {

        EpicDto epicDto = epicService.save(new CreateEpicRq("nameEpicForGetAllByName", "descriptionEpicForGetAllByName", ProgressStatus.DONE,
                Priority.MEDIUM, "projectIdEpicForGetAllByName", "authorIdEpicForGetAllByName", "superSprintIdEpicForGetAllByName"));
        this.mockMvc.perform(get("/api/epic/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("filter", epicDto.getName())
                        .param("pageNumber", "1")
                        .param("pageSize", "32"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(1)))
                .andExpect(jsonPath("$.pageSize", is(32)));
        this.mockMvc.perform(get("/api/epic/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("filter", epicDto.getName())
                        .param("pageNumber", "6")
                        .param("pageSize", "756"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(6)))
                .andExpect(jsonPath("$.pageSize", is(40)));
    }

    @Test
    void getById() throws Exception {

        EpicDto epicDto = epicService.save(new CreateEpicRq("nameEpicForGerById", "descriptionEpicForGerById", ProgressStatus.DONE,
                Priority.MEDIUM, "projectIdEpicForGerById", "authorIdEpicForGerById", "superSprintIdEpicForGerById"));
        this.mockMvc.perform(get("/api/epic/{id}", epicDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(epicDto.getId())));
    }
}