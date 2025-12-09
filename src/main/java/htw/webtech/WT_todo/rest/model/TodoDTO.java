package htw.webtech.WT_todo.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
    private Long id;
    private String title;
    private boolean done;

    public TodoDTO() {
    }

    public TodoDTO(Long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }
}
