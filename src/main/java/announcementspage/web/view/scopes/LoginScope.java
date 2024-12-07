package announcementspage.web.view.scopes;

import commons.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

public class LoginScope {
    @Getter
    @Setter
    private String loginName;
    @Getter
    @Setter
    private String password;

    private boolean procesed;


    private void setProfileAndGoToMainPage (UserDto userProfileDto) {
        //TODO
    }

    public void loginUser() {
        //TODO
    }
}
