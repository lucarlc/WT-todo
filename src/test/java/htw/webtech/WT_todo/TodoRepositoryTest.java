package htw.webtech.WT_todo;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import htw.webtech.WT_todo.persistence.entity.UserEntity;
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

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldDeleteAllCompletedTodosForSpecificUserOnly() {
        UserEntity userA = userRepository.save(new UserEntity("alice", "x"));
        UserEntity userB = userRepository.save(new UserEntity("bob", "x"));

        TodoEntity a1 = new TodoEntity();
        a1.setTitle("A1");
        a1.setDone(true);
        a1.setUser(userA);

        TodoEntity a2 = new TodoEntity();
        a2.setTitle("A2");
        a2.setDone(false);
        a2.setUser(userA);

        TodoEntity b1 = new TodoEntity();
        b1.setTitle("B1");
        b1.setDone(true);
        b1.setUser(userB);

        todoRepository.save(a1);
        todoRepository.save(a2);
        todoRepository.save(b1);

        int deleted = todoRepository.deleteByUserAndDoneTrue(userA);

        assertThat(deleted).isEqualTo(1);
        assertThat(todoRepository.findByUser(userA)).hasSize(1);
        assertThat(todoRepository.findByUser(userA).get(0).getTitle()).isEqualTo("A2");

        assertThat(todoRepository.findByUser(userB)).hasSize(1);
        assertThat(todoRepository.findByUser(userB).get(0).getTitle()).isEqualTo("B1");
    }
}
