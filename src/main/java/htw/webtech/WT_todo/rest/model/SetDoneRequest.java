package htw.webtech.WT_todo.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetDoneRequest {

    private boolean done;

    public SetDoneRequest() {
    }
}