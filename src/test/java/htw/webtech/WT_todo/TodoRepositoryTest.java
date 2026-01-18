package htw.webtech.WT_todo;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import htw.webtech.WT_todo.persistence.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldDeleteAllCompletedTodos() {
        TodoEntity a = new TodoEntity();
        a.setTitle("A");
        a.setDone(true);

        TodoEntity b = new TodoEntity();
        b.setTitle("B");
        b.setDone(false);

        todoRepository.save(a);
        todoRepository.save(b);

        int deleted = todoRepository.deleteByDoneTrue();
        assertThat(deleted).isEqualTo(1);
        assertThat(todoRepository.findAll()).hasSize(1);
        assertThat(todoRepository.findAll().get(0).isDone()).isFalse();
    }
}
