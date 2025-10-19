package htw.webtech.WT_todo.rest.controller;

import htw.webtech.WT_todo.rest.model.TodoDTO;
import htw.webtech.WT_todo.business.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoDTO>> getTodos() {
        return ResponseEntity.ok(service.getAllTodos());
    }
}


