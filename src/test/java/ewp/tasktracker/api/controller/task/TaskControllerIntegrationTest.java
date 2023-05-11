package ewp.tasktracker.api.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.task.CreateTaskRq;
import ewp.tasktracker.api.dto.task.TaskDto;
import ewp.tasktracker.entity.task.TaskEntity;
import ewp.tasktracker.enums.Priority;
import ewp.tasktracker.enums.ProgressStatus;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.service.task.TaskServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskServiceImp taskService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void should_get_all_tasks() throws Exception {

        PageDto<TaskDto> page = taskService.findAll(PageRequest.of(0, 20));

        ResultActions response = mockMvc.perform(get("/api/task"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()", is(page.getItems().size())));
    }

    @Test
    void should_get_task_by_id() throws Exception {
        CreateTaskRq createTaskRq = createTaskRequest();

        TaskEntity taskEntity = taskService.save(createTaskRq);

        ResultActions response = mockMvc.perform(get("/api/task/{id}",
                taskEntity.getId()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskEntity.getId())));
    }

    @Test
    void should_not_find_task_by_id() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/task/555"));
        response.andExpect(status().is4xxClientError());
    }



    @Test
    void should_save_task() throws Exception {
        CreateTaskRq createTaskRq = createTaskRequest();

        ResultActions response = mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTaskRq)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(createTaskRq.getName())))
                .andExpect(jsonPath("$.description",
                        is(createTaskRq.getDescription())))
                .andExpect(jsonPath("$.status",
                        is("DONE")))
                .andExpect(jsonPath("$.priority",
                        is("LOW")))
                .andExpect(jsonPath("$.historyId",
                        is(createTaskRq.getHistoryId())))
                .andExpect(jsonPath("$.authorId",
                        is(createTaskRq.getAuthorId())))
                .andExpect(jsonPath("$.assigneeId",
                        is(createTaskRq.getAssigneeId())));

    }

    @Test
    void should_not_saveTask() throws Exception {
        CreateTaskRq createTaskRq = new CreateTaskRq("a", "a", ProgressStatus.DONE, Priority.LOW,
                "history id", "author id", "assignee id");

        ResultActions response = mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTaskRq)));

        response.andExpect(status().is4xxClientError());
    }

    @Test
    void should_update_task() throws Exception {
        CreateTaskRq saveTask = createTaskRequest();

        TaskEntity taskEntity = taskService.save(saveTask);

        taskEntity.setName("updated name");
        taskEntity.setDescription("updated desc");
        taskEntity.setHistoryId("update history");
        taskEntity.setAuthorId("update author id");
        taskEntity.setAssigneeId("update assignee id");

        ResultActions response = mockMvc.perform(put("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskEntity)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is("updated name")))
                .andExpect(jsonPath("$.description",
                        is("updated desc")))
                .andExpect(jsonPath("$.status",
                        is("DONE")))
                .andExpect(jsonPath("$.priority",
                        is("LOW")))
                .andExpect(jsonPath("$.historyId",
                        is("update history")))
                .andExpect(jsonPath("$.authorId",
                        is("update author id")))
                .andExpect(jsonPath("$.assigneeId",
                        is("update assignee id")));
    }

    @Test
    void should_delete_task() throws Exception {
        CreateTaskRq saveTask = createTaskRequest();

        TaskEntity taskEntity = taskService.save(saveTask);

        ResultActions response = mockMvc.perform(delete("/api/task/{id}", taskEntity.getId()));

        response.andExpect(status().isOk());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> taskService.getById(taskEntity.getId()));
    }




    private CreateTaskRq createTaskRequest() {
        return new CreateTaskRq("saved name", "saved desc", ProgressStatus.DONE, Priority.LOW,
                "history id", "author id", "assignee id");
    }

}
