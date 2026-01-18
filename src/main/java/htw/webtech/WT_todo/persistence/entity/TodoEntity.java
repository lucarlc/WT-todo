package htw.webtech.WT_todo.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * TodoEntity ist das Abbild der Datenbanktabelle "todos".
 * Jede Instanz entspricht einer Zeile in dieser Tabelle.
 */
@Entity                     // markiert die Klasse als JPA Entity
@Table(name = "todos")      // Name der Tabelle in der Datenbank
public class TodoEntity {

    @Id                     // Primärschlüssel Spalte
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Wert wird von der Datenbank erzeugt
    private Long id;

    @Column(nullable = false)
    // Spalte "title", darf nicht null sein
    private String title;

    // Spalte "done", Standard ist false
    private boolean done;

    /**
     * Besitzer des Todos. Darf (aus Migrationsgruenden) NULL sein,
     * aber in der App werden Todos immer einem User zugeordnet.
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Leerer Konstruktor ist für JPA Pflicht!
     * JPA erzeugt über Reflection Objekte und braucht das
     */
    public TodoEntity() {
    }

    /**
     * Praktischer Konstruktor für eigene Nutzung im Code.
     */
    public TodoEntity(String title, boolean done) {
        this.title = title;
        this.done = done;
    }

    public TodoEntity(UserEntity user, String title, boolean done) {
        this.user = user;
        this.title = title;
        this.done = done;
    }

    // Getter und Setter, damit JPA und dein Code auf die Felder zugreifen können

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
