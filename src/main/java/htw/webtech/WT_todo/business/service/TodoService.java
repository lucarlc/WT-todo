package htw.webtech.WT_todo.business.service;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import htw.webtech.WT_todo.persistence.repository.TodoRepository;
import htw.webtech.WT_todo.rest.model.TodoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    /**
     * Holt alle Todos aus Datenbank,
     * mappt die Entity Liste in eine DTO Liste
     * und gibt sie an den Controller weiter.
     */
    public List<TodoDTO> getAllTodos() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)   // Entity in DTO umwandeln
                .toList();
    }

    /**
     * Erzeugt ein neues Todo in der Datenbank.
     * Der Controller gibt ein DTO rein, wir speichern die Entität
     * und liefern das gespeicherte DTO zurück.
     */
    public TodoDTO createTodo(TodoDTO dto) {
        TodoEntity entity = mapToEntity(dto);
        entity.setId(null);               // sicherstellen, dass eine neue Zeile erzeugt wird

        TodoEntity saved = repository.save(entity);
        return mapToDto(saved);
    }

    /* Hilfsfunktion: Entity nach DTO */

    private TodoDTO mapToDto(TodoEntity entity) {
        return new TodoDTO(
                entity.getId(),
                entity.getTitle(),
                entity.isDone()
        );
    }

    /* Hilfsfunktion: DTO nach Entity */

    private TodoEntity mapToEntity(TodoDTO dto) {
        TodoEntity entity = new TodoEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDone(dto.isDone());
        return entity;
    }
}


