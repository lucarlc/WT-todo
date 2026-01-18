package htw.webtech.WT_todo.business.service;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import htw.webtech.WT_todo.persistence.entity.UserEntity;
import htw.webtech.WT_todo.persistence.repository.TodoRepository;
import htw.webtech.WT_todo.rest.model.CreateTodoRequest;
import htw.webtech.WT_todo.rest.model.TodoDTO;
import htw.webtech.WT_todo.rest.model.UpdateTodoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;

    public TodoService(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    public List<TodoDTO> getAll(String username, Boolean done, String query) {
        UserEntity user = userService.requireByUsername(username);
        List<TodoEntity> entities;

        if (done != null) {
            entities = todoRepository.findByUserAndDone(user, done);
        } else if (query != null && !query.isBlank()) {
            entities = todoRepository.findByUserAndTitleContainingIgnoreCase(user, query.trim());
        } else {
            entities = todoRepository.findByUser(user);
        }

        return entities.stream().map(this::toDto).toList();
    }

    public TodoDTO getById(String username, long id) {
        UserEntity user = userService.requireByUsername(username);
        TodoEntity entity = requireOwnedTodo(user, id);
        return toDto(entity);
    }

    public TodoDTO create(String username, CreateTodoRequest request) {
        UserEntity user = userService.requireByUsername(username);
        TodoEntity entity = new TodoEntity(user, request.getTitle().trim(), request.isDone());
        TodoEntity saved = todoRepository.save(entity);
        return toDto(saved);
    }

    public TodoDTO update(String username, long id, UpdateTodoRequest request) {
        UserEntity user = userService.requireByUsername(username);
        TodoEntity entity = requireOwnedTodo(user, id);

        entity.setTitle(request.getTitle().trim());
        entity.setDone(request.isDone());

        TodoEntity saved = todoRepository.save(entity);
        return toDto(saved);
    }

    public TodoDTO setDone(String username, long id, boolean done) {
        UserEntity user = userService.requireByUsername(username);
        TodoEntity entity = requireOwnedTodo(user, id);

        entity.setDone(done);
        TodoEntity saved = todoRepository.save(entity);
        return toDto(saved);
    }

    public void delete(String username, long id) {
        UserEntity user = userService.requireByUsername(username);
        TodoEntity entity = requireOwnedTodo(user, id);
        todoRepository.deleteById(entity.getId());
    }

    @Transactional
    public void deleteCompleted(String username) {
        UserEntity user = userService.requireByUsername(username);
        todoRepository.deleteByUserAndDoneTrue(user);
    }

    private TodoEntity requireOwnedTodo(UserEntity user, long todoId) {
        TodoEntity entity = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo nicht gefunden"));

        // Migrationstolerant: falls alte Todos keine user_id haben, gelten sie nicht als "owned".
        if (entity.getUser() == null || entity.getUser().getId() == null || !entity.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Todo nicht gefunden");
        }
        return entity;
    }

    private TodoDTO toDto(TodoEntity entity) {
        return new TodoDTO(entity.getId(), entity.getTitle(), entity.isDone());
    }
}


