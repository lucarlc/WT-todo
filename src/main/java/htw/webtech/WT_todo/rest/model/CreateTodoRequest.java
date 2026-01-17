package htw.webtech.WT_todo.rest.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoRequest {

    @NotBlank(message = "title darf nicht leer sein")
    private String title;

    private boolean done;

    public CreateTodoRequest() {
    }
}
