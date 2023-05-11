package ewp.tasktracker.api.controller.bug;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.bug.BugDto;
import ewp.tasktracker.api.dto.bug.CreateBugRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.entity.bug.BugEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.service.bug.BugServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BugControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BugServiceImpl bugService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should_get_all_bugs() throws Exception {

        PageDto<BugDto> page = bugService.findAll(PageRequest.of(0, 20));

        ResultActions response = mockMvc.perform(get("/api/bug"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()", is(page.getItems().size())));
    }

    @Test
    void should_get_but_by_id() throws Exception {
        CreateBugRq createBugRq = createBugRequest();

        BugEntity bugEntity = bugService.save(createBugRq);

        ResultActions response = mockMvc.perform(get("/api/bug/{id}",
                bugEntity.getId()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bugEntity.getId())));
    }

    @Test
    void should_not_find_bug_by_id() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/bug/123"));
        response.andExpect(status().is4xxClientError());
    }

    @Test
    void should_save_bug() throws Exception {
        CreateBugRq createBugRq = createBugRequest();

        ResultActions response = mockMvc.perform(post("/api/bug")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBugRq)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(createBugRq.getName())))
                .andExpect(jsonPath("$.description",
                        is(createBugRq.getDescription())));
    }

    @Test
    void should_not_save_bug() throws Exception {
        CreateBugRq createBugRq = new CreateBugRq("a", "b", ProgressStatus.DONE, Priority.LOW,
                "history id", "author id", "assignee id");

        ResultActions response = mockMvc.perform(post("/api/bug")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBugRq)));

        response.andExpect(status().is4xxClientError());
    }

    @Test
    void should_update_bug() throws Exception {
        CreateBugRq savedBug = createBugRequest();

        BugEntity bugEntity = bugService.save(savedBug);

        bugEntity.setName("updated name");
        bugEntity.setDescription("updated desc");

        ResultActions response = mockMvc.perform(put("/api/bug")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bugEntity)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is("updated name")))
                .andExpect(jsonPath("$.description",
                        is("updated desc")));
    }

    @Test
    void should_delete_bug() throws Exception {
        CreateBugRq savedBug = createBugRequest();

        BugEntity bugEntity = bugService.save(savedBug);

        ResultActions response = mockMvc.perform(delete("/api/bug/{id}", bugEntity.getId()));

        response.andExpect(status().isOk());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bugService.findById(bugEntity.getId()));
    }

    private CreateBugRq createBugRequest() {
        return new CreateBugRq("saved name", "saved desc", ProgressStatus.DONE, Priority.LOW,
                "history id", "author id", "assignee id");
    }
}