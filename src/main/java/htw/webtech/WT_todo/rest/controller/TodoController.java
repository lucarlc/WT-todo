package htw.webtech.WT_todo.rest.controller;

import htw.webtech.WT_todo.business.service.TodoService;
import htw.webtech.WT_todo.rest.model.CreateTodoRequest;
import htw.webtech.WT_todo.rest.model.SetDoneRequest;
import htw.webtech.WT_todo.rest.model.TodoDTO;
import htw.webtech.WT_todo.rest.model.UpdateTodoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoDTO> getAll(
            Authentication auth,
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String q
    ) {
        return todoService.getAll(auth.getName(), done, q);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDTO create(Authentication auth, @Valid @RequestBody CreateTodoRequest request) {
        return todoService.create(auth.getName(), request);
    }

    @PutMapping("/{id}")
    public TodoDTO update(Authentication auth, @PathVariable long id, @Valid @RequestBody UpdateTodoRequest request) {
        return todoService.update(auth.getName(), id, request);
    }

    @PatchMapping("/{id}/done")
    public TodoDTO setDone(Authentication auth, @PathVariable long id, @Valid @RequestBody SetDoneRequest request) {
        return todoService.setDone(auth.getName(), id, request.isDone());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication auth, @PathVariable long id) {
        todoService.delete(auth.getName(), id);
    }

    @DeleteMapping("/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompleted(Authentication auth) {
        todoService.deleteCompleted(auth.getName());
    }
}


