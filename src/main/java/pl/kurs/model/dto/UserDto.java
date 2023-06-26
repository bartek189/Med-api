package pl.kurs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.model.entity.User;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long id;
    private String userName;
    private String email;
    private String password;

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
