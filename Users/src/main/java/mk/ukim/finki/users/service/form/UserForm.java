package mk.ukim.finki.users.service.form;

import lombok.Data;

@Data
public class UserForm {
    String id;
    String username;

    public UserForm(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
