package htw.webtech.WT_todo.persistence.repository;

import htw.webtech.WT_todo.persistence.entity.TodoEntity;
import htw.webtech.WT_todo.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
    List<TodoEntity> findByUser(UserEntity user);

    List<TodoEntity> findByUserAndDone(UserEntity user, boolean done);

    List<TodoEntity> findByUserAndTitleContainingIgnoreCase(UserEntity user, String query);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TodoEntity t where t.user = :user and t.done = true")
    int deleteByUserAndDoneTrue(UserEntity user);
}
