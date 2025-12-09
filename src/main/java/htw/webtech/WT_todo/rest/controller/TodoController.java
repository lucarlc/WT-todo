package htw.webtech.WT_todo.rest.controller;

import htw.webtech.WT_todo.rest.model.TodoDTO;
import htw.webtech.WT_todo.business.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDTO createTodo(@RequestBody TodoDTO todo) {
        return service.createTodo(todo);
    }
}


