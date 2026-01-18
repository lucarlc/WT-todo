package htw.webtech.WT_todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.webtech.WT_todo.rest.model.AuthResponse;
import htw.webtech.WT_todo.rest.model.CreateTodoRequest;
import htw.webtech.WT_todo.rest.model.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTodoAndReturnItInList() throws Exception {
        String token = registerAndGetToken(uniqueUsername(), "secret123");

        CreateTodoRequest req = new CreateTodoRequest();
        req.setTitle("Demo Todo");
        req.setDone(false);

        mockMvc.perform(post("/api/v1/todos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Demo Todo")))
                .andExpect(jsonPath("$.done", is(false)));

        mockMvc.perform(get("/api/v1/todos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Demo Todo")));
    }

    @Test
    void shouldReturn400ForEmptyTitle() throws Exception {
        String token = registerAndGetToken(uniqueUsername(), "secret123");

        CreateTodoRequest req = new CreateTodoRequest();
        req.setTitle("   ");
        req.setDone(false);

        mockMvc.perform(post("/api/v1/todos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Validierung")));
    }

    private String registerAndGetToken(String username, String password) throws Exception {
        RegisterRequest r = new RegisterRequest();
        r.setUsername(username);
        r.setPassword(password);

        String body = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse resp = objectMapper.readValue(body, AuthResponse.class);
        return resp.getToken();
    }

    private String uniqueUsername() {
        return "user" + System.nanoTime();
    }
}
