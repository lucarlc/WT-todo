package htw.webtech.WT_todo.business.service;

import htw.webtech.WT_todo.rest.model.TodoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    public List<TodoDTO> getAllTodos() {
        return List.of(
                new TodoDTO(1L, "Milestone 1 abschließen", false),
                new TodoDTO(2L, "Repo-Link mailen", false),
                new TodoDTO(3L, "Demo vorbereiten", true)
        );
    }
}


