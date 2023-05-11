package ewp.tasktracker.api.controller.supersprint;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.supersprint.CreateSupersprintRq;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.api.dto.supersprint.SupersprintDto;
import ewp.tasktracker.service.supersprint.SupersprintServiceImpl;
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
class SupersprintControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SupersprintServiceImpl supersprintService;


    @Test
    void save() throws Exception {

        this.mockMvc.perform(post("/api/supersprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateSupersprintRq("name",
                                        LocalDateTime.parse("2024-02-15T22:00:06"),
                                        LocalDateTime.parse("2025-02-15T22:00:06"),
                                        "authorId"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is("name")))
                .andExpect(jsonPath("$.authorId",
                        is("authorId")));
        this.mockMvc.perform(post("/api/supersprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateSupersprintRq("name",
                                        LocalDateTime.parse("2024-02-15T22:00:06"),
                                        LocalDateTime.parse("2025-02-15T22:00:06"),
                                        "authorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorIdauthorId"))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        PageDto<SupersprintDto> page = supersprintService.findAllByPageRequest(PageRequest.of(3, 24));
        this.mockMvc.perform(get("/api/supersprint")
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

        SupersprintDto supersprintDto = supersprintService.save(new CreateSupersprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId"));
        supersprintDto.setName("newName");
        supersprintDto.setStartAt(LocalDateTime.parse("2025-02-15T22:00:06"));
        supersprintDto.setEndAt(LocalDateTime.parse("2032-02-15T22:00:06"));
        supersprintDto.setAuthorId("newAuthorId");
        this.mockMvc.perform(put("/api/supersprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supersprintDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        is(supersprintDto.getId())))
                .andExpect(jsonPath("$.name",
                        is(supersprintDto.getName())))
                .andExpect(jsonPath("$.startAt",
                        is(supersprintDto.getStartAt() + "Z")))
                .andExpect(jsonPath("$.endAt",
                        is(supersprintDto.getEndAt() + "Z")))
                .andExpect(jsonPath("$.authorId",
                        is(supersprintDto.getAuthorId())));
        supersprintDto.setName("newName");
        supersprintDto.setAuthorId("newSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintIdnewSupersprintId");
        this.mockMvc.perform(put("/api/supersprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supersprintDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAllByName() throws Exception {

        SupersprintDto supersprintDto = supersprintService.save(new CreateSupersprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId"));
        this.mockMvc.perform(get("/api/supersprint/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("filter", supersprintDto.getName())
                        .param("pageNumber", "1")
                        .param("pageSize", "32"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(1)))
                .andExpect(jsonPath("$.pageSize", is(32)));
        this.mockMvc.perform(get("/api/epic/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("filter", supersprintDto.getName())
                        .param("pageNumber", "6")
                        .param("pageSize", "756"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(6)))
                .andExpect(jsonPath("$.pageSize", is(40)));
    }

    @Test
    void getById() throws Exception {

        SupersprintDto supersprintDto = supersprintService.save(new CreateSupersprintRq("name",
                LocalDateTime.parse("2024-02-15T22:00:06"),
                LocalDateTime.parse("2025-02-15T22:00:06"),
                "authorId"));
        this.mockMvc.perform(get("/api/supersprint/{id}", supersprintDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(supersprintDto.getId())));
    }
}