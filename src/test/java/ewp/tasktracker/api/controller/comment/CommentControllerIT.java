package ewp.tasktracker.api.controller.comment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewp.tasktracker.api.dto.comment.CreateCommentRq;
import ewp.tasktracker.api.dto.comment.UpdateCommentRq;
import ewp.tasktracker.entity.comment.CommentEntity;
import ewp.tasktracker.exception.ResourceNotFoundException;
import ewp.tasktracker.service.comment.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CommentControllerIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CommentService service;

    @Test
    public void whenPostRequestAndValidComment_thenCorrectResponse() throws Exception {
        CreateCommentRq createCommentRq = new CreateCommentRq("Blah", "234", "567");

        this.mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value(createCommentRq.getText()));
    }

    @Test
    public void whenPostRequestAndInvalidComment_thenCorrectResponse() throws Exception {
        CreateCommentRq createCommentRq = new CreateCommentRq(null, "234", "567");

        this.mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).contains("Text must not be null"))
        ;
    }

    @Test
    public void whenPutRequestAndValidComment_thenCorrectResponse() throws Exception {
        CreateCommentRq createCommentRq = new CreateCommentRq("Blah", "234", "567");
        CommentEntity commentEntity = service.save(createCommentRq);
        UpdateCommentRq updateCommentRq = new UpdateCommentRq(commentEntity.getId(), "Blah blah blah");

        this.mockMvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.text").value(updateCommentRq.getText()));
    }

    @Test
    public void whenPutRequestAndInvalidComment_thenCorrectResponse() throws Exception {
        CreateCommentRq createCommentRq = new CreateCommentRq("Blah", "234", "567");
        CommentEntity commentEntity = service.save(createCommentRq);
        UpdateCommentRq updateCommentRq = new UpdateCommentRq(commentEntity.getId(), "");

        this.mockMvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRq))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).contains("Text must not be empty"))
        ;
    }

    @Test
    public void whenDeleteCommentById_thenNoContent204() throws Exception {
        CreateCommentRq createCommentRq = new CreateCommentRq("Blah", "234", "567");
        CommentEntity commentEntity = service.save(createCommentRq);

        this.mockMvc.perform(delete("/api/comments/{id}", commentEntity.getId())
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent())
                ;

        assertThrows(ResourceNotFoundException.class, () -> service.findById(commentEntity.getId()));
    }

    @Test
    public void whenGetAllCommentsByTaskId_thenOK() throws Exception {
        createDummieData();

        this.mockMvc.perform(get("/api/comments?taskId=567")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()", is(4)))
        ;
    }

    private void createDummieData() {
        CreateCommentRq createCommentRq = new CreateCommentRq("Blah", "234", "567");
        CommentEntity commentEntity = service.save(createCommentRq);
        createCommentRq = new CreateCommentRq("Well done!", "234", "567");
        service.save(createCommentRq);
        createCommentRq = new CreateCommentRq("Ups", "234", "789");
        service.save(createCommentRq);
        createCommentRq = new CreateCommentRq("Refactor", "234", "567");
        service.save(createCommentRq);
        createCommentRq = new CreateCommentRq("Comment", "234", "567");
        service.save(createCommentRq);
    }
}
