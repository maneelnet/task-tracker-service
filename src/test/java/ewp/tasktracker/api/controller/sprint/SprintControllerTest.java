package ewp.tasktracker.api.controller.sprint;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.sprint.CreateSprintRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.sprint.SprintDto;
import ewp.tasktracker.service.sprint.SprintServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SprintControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SprintServiceImpl sprintService;


    @Test
    void save() throws Exception {

        this.mockMvc.perform(post("/api/sprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateSprintRq("name",
                                        LocalDateTime.parse("2024-02-15T22:00:06"),
                                        LocalDateTime.parse("2025-02-15T22:00:06"),
                                        "authorId", "supersprintId"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is("name")))
                .andExpect(jsonPath("$.authorId",
                        is("authorId")))
                .andExpect(jsonPath("$.supersprintId",
                        is("supersprintId")));
        this.mockMvc.perform(post("/api/sprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateSprintRq("name",
                                        LocalDateTime.parse("2024-02-15T22:00:06"),
                                        LocalDateTime.parse("2025-02-15T22:00:06"),
                                        "authorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorId",
                                        "supersprintId"))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        PageDto<SprintDto> page = sprintService.findAllByPageRequest(PageRequest.of(3, 24));
        this.mockMvc.perform(get("/api/sprint")
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

        SprintDto sprintDto = sprintService.save(new CreateSprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId"));
        sprintDto.setName("newName");
        sprintDto.setStartAt(LocalDateTime.parse("2025-02-15T22:00:06"));
        sprintDto.setEndAt(LocalDateTime.parse("2032-02-15T22:00:06"));
        sprintDto.setAuthorId("newAuthorId");
        sprintDto.setSupersprintId("newSupersprintId");
        this.mockMvc.perform(put("/api/sprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sprintDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        is(sprintDto.getId())))
                .andExpect(jsonPath("$.name",
                        is(sprintDto.getName())))
                .andExpect(jsonPath("$.startAt",
                        is(sprintDto.getStartAt() + "Z")))
                .andExpect(jsonPath("$.endAt",
                        is(sprintDto.getEndAt() + "Z")))
                .andExpect(jsonPath("$.authorId",
                        is(sprintDto.getAuthorId())))
                .andExpect(jsonPath("$.supersprintId",
                        is(sprintDto.getSupersprintId())));
        sprintDto.setName("newName");
        sprintDto.setAuthorId("newSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintId");
        this.mockMvc.perform(put("/api/sprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sprintDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAllByName() throws Exception {

        SprintDto sprintDto = sprintService.save(new CreateSprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId"));
        this.mockMvc.perform(get("/api/sprint/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("filter", sprintDto.getName())
                        .param("pageNumber", "1")
                        .param("pageSize", "32"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(1)))
                .andExpect(jsonPath("$.pageSize", is(32)));
        this.mockMvc.perform(get("/api/epic/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("filter", sprintDto.getName())
                        .param("pageNumber", "6")
                        .param("pageSize", "756"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(6)))
                .andExpect(jsonPath("$.pageSize", is(40)));
    }

    @Test
    void getById() throws Exception {

        SprintDto sprintDto = sprintService.save(new CreateSprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId", "supersprintId"));
        this.mockMvc.perform(get("/api/sprint/{id}", sprintDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sprintDto.getId())));
    }
}