package htw.webtech.WT_todo.rest.controller;

import htw.webtech.WT_todo.business.service.TodoService;
import htw.webtech.WT_todo.rest.model.CreateTodoRequest;
import htw.webtech.WT_todo.rest.model.SetDoneRequest;
import htw.webtech.WT_todo.rest.model.TodoDTO;
import htw.webtech.WT_todo.rest.model.UpdateTodoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String q
    ) {
        return todoService.getAll(done, q);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDTO create(@Valid @RequestBody CreateTodoRequest request) {
        return todoService.create(request);
    }

    @PutMapping("/{id}")
    public TodoDTO update(@PathVariable long id, @Valid @RequestBody UpdateTodoRequest request) {
        return todoService.update(id, request);
    }

    @PatchMapping("/{id}/done")
    public TodoDTO setDone(@PathVariable long id, @Valid @RequestBody SetDoneRequest request) {
        return todoService.setDone(id, request.isDone());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        todoService.delete(id);
    }

    @DeleteMapping("/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompleted() {
        todoService.deleteCompleted();
    }
}


