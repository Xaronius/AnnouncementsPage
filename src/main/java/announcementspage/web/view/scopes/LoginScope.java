
package announcementspage.web.view.scopes;

import com.example.announcementspage.services.UserService;
import commons.dto.UserDto;
import commons.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class LoginScope {

    private final UserService userService;
    @Getter
    @Setter
    private String loginName;
    @Getter
    @Setter
    private String password;

    private boolean procesed;

    @Autowired
    public LoginScope(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public User getUserByUsername(@PathVariable String userName) {
        return userService.findUserByUsername(userName);
    }
    private void setProfileAndGoToMainPage (UserDto userProfileDto) {
        //TODO
    }

    public void loginUser() {
        //TODO
    }
}