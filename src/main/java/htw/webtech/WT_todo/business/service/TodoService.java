package htw.webtech.WT_todo.business.service;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import htw.webtech.WT_todo.persistence.repository.TodoRepository;
import htw.webtech.WT_todo.rest.model.CreateTodoRequest;
import htw.webtech.WT_todo.rest.model.TodoDTO;
import htw.webtech.WT_todo.rest.model.UpdateTodoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> getAll(Boolean done, String query) {
        List<TodoEntity> entities;

        if (done != null) {
            entities = todoRepository.findByDone(done);
        } else if (query != null && !query.isBlank()) {
            entities = todoRepository.findByTitleContainingIgnoreCase(query.trim());
        } else {
            entities = todoRepository.findAll();
        }

        return entities.stream().map(this::toDto).toList();
    }

    public TodoDTO getById(long id) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo nicht gefunden"));
        return toDto(entity);
    }

    public TodoDTO create(CreateTodoRequest request) {
        TodoEntity entity = new TodoEntity(request.getTitle().trim(), request.isDone());
        TodoEntity saved = todoRepository.save(entity);
        return toDto(saved);
    }

    public TodoDTO update(long id, UpdateTodoRequest request) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo nicht gefunden"));

        entity.setTitle(request.getTitle().trim());
        entity.setDone(request.isDone());

        TodoEntity saved = todoRepository.save(entity);
        return toDto(saved);
    }

    public TodoDTO setDone(long id, boolean done) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo nicht gefunden"));

        entity.setDone(done);
        TodoEntity saved = todoRepository.save(entity);
        return toDto(saved);
    }

    public void delete(long id) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("Todo nicht gefunden");
        }
        todoRepository.deleteById(id);
    }

    @Transactional
    public long deleteCompleted() {
        return todoRepository.deleteByDoneTrue();
    }

    private TodoDTO toDto(TodoEntity entity) {
        return new TodoDTO(entity.getId(), entity.getTitle(), entity.isDone());
    }
}


