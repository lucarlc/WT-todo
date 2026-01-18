package htw.webtech.WT_todo.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Username darf nicht leer sein")
    @Size(max = 64, message = "Username ist zu lang")
    private String username;

    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(min = 6, max = 100, message = "Passwort muss zwischen 6 und 100 Zeichen haben")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
