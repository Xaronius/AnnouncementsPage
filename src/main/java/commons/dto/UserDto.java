
package commons.dto;

import commons.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String email;
    private String password;
    private Date passwordDate;
    private boolean userBlocked;

    public UserDto (User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
    }
}