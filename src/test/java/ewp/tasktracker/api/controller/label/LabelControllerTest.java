package ewp.tasktracker.api.controller.label;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.label.CreateLabelRq;
import ewp.tasktracker.api.dto.label.LabelDto;
import ewp.tasktracker.api.dto.response.PageDto;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.service.label.LabelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LabelServiceImpl labelService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void save() throws Exception {
        this.mockMvc.perform(post("/api/label")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateLabelRq("text", "authorId", "taskId"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text",
                        is("text")))
                .andExpect(jsonPath("$.authorId",
                        is("authorId")))
                .andExpect(jsonPath("$.taskId",
                        is("taskId")));
        this.mockMvc.perform(post("/api/label")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateLabelRq("text",
                                "1234567890123456789012345678901234567hereManyTo36Symbols",
                                "1234567890123456789012345678901234567hereManyTo36Symbols"))))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getLabelsForTaskIdAndSizeOrElseGetLabelsForSize() throws Exception {
        LabelDto labelDto = labelService.save( new CreateLabelRq("forGetAllTestText",
                "forGetAllTestAuthorId", "forGetAllTestTaskId"));
        String str = null;
        PageDto<LabelDto> page = labelService.findAllByPageRequest(str, PageRequest.of(3, 24));
        this.mockMvc.perform(get("/api/label")
                        .param("taskId", labelDto.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
        //создаем
        LabelDto labelDto = labelService.save( new CreateLabelRq("forUpdateTestText",
                "forUpdateTestAuthorId", "forUpdateTestTaskId"));
        //обновляем созданный объект новыми полями
        labelDto.setText("someText");
        labelDto.setAuthorId("someAuthorId");
        labelDto.setTaskId("someTaskId");
        //посылаем PUT запрос и проверяем обновленные поля
        this.mockMvc.perform(put("/api/label")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text",
                        is("someText")))
                .andExpect(jsonPath("$.authorId",
                        is("someAuthorId")))
                .andExpect(jsonPath("$.taskId",
                        is("someTaskId")));
        labelDto.setText("someText");
        labelDto.setAuthorId("someAuthorIdsomeAuthorIdsomeAuthorIdsomeAuthorIdsomeAuthorIdsomeAuthorIdsomeAuthorIdsomeAuthorId");
        labelDto.setTaskId("someTaskId");
        this.mockMvc.perform(put("/api/label")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getRemoveLabel() throws Exception {

        LabelDto labelDto = labelService.save( new CreateLabelRq("forRemoveTestText",
                "forRemoveTestAuthorId", "forRemoveTestTaskId"));
        //1. Создать лейбл через запрос или через вызов сервиса,
        String getIdFromLabelDto = labelDto.getId();
        //забрать идентификатор из полученного объекта
        mockMvc.perform(delete("/api/label/{id}", getIdFromLabelDto) //2. Вызвать запрос удаления лейбла с этим id
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> labelService.findById(labelDto.getId()));
        //3. Вызвать запрос получение лейбла по этому id и убедиться, что в ответе - сущность не найдена
    }
}