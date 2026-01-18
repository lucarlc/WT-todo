package htw.webtech.WT_todo.persistence.repository;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * TodoRepository ist die Schnittstelle zur Datenbank.
 * Durch JpaRepository:
 *  findAll, findById, save, deleteById und weitere.
 *
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    // Zusätzliche Query Methoden kommen später hier hin, z B findByDone(boolean done)
    List<TodoEntity> findByDone(boolean done);

    List<TodoEntity> findByTitleContainingIgnoreCase(String query);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TodoEntity t where t.done = true")
    int deleteByDoneTrue();
}
