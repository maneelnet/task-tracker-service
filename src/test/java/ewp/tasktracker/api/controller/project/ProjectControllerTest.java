package ewp.tasktracker.api.controller.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.project.CreateProjectRq;
import ewp.tasktracker.entity.project.ProjectEntity;
import ewp.tasktracker.enums.Status;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.repository.project.ProjectRepository;
import ewp.tasktracker.service.project.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void saveControllerTest() throws Exception {
        CreateProjectRq projectRq = getCreateProjectRq();
        mockMvc.perform(post("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(projectRq.getName())))
                .andExpect(jsonPath("$.description",
                        is(projectRq.getDescription())));
    }

    @Test
    void failSaveControllerTest() throws Exception {
        CreateProjectRq createProjectRq = getCreateProjectRq();
        createProjectRq.setName("f");
        mockMvc.perform(post("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProjectRq)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getByIdControllerTest() throws Exception {
        ProjectEntity saveEntity = projectService.save(getCreateProjectRq());
        mockMvc.perform(get("/api/project/{id}", saveEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saveEntity.getId())));
    }

    @Test
    void failGetByIdControllerTest() throws Exception {
        mockMvc
                .perform(get("/api/project/fail"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        projectRepository.deleteAll();
        List<ProjectEntity> projectEntities = List.of(
                projectService.save(getCreateProjectRq()),
                projectService.save(getCreateProjectRq()));
        mockMvc.perform(get("/api/project"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(projectEntities.size())));
    }

    @Test
    void deleteById() throws Exception {
        ProjectEntity projectEntity = projectService.save(getCreateProjectRq());
        mockMvc.perform(delete("/api/project/{id}", projectEntity.getId()))
                .andExpect(status().isOk());
        assertThrows(ResourceNotFoundException.class,
                () -> projectService.findById(projectEntity.getId()));
    }

    @Test
    void update() throws Exception {
        ProjectEntity projectEntity = projectService.save(getCreateProjectRq());
        projectEntity.setName("update");
        projectEntity.setDescription("desc_update");
        mockMvc.perform(put("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is("update")))
                .andExpect(jsonPath("$.description",
                        is("desc_update")));

    }

    private static CreateProjectRq getCreateProjectRq() {
        return CreateProjectRq.builder()
                .name("name")
                .description("description")
                .status(Status.ACTIVE)
                .workloadId("workload_id")
                .author_id("author_id")
                .build();
    }
}