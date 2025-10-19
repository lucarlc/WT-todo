package htw.webtech.WT_todo.rest.model;

public class TodoDTO {
    private Long id;
    private String title;
    private boolean done;

    public TodoDTO() { }

    public TodoDTO(Long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}
