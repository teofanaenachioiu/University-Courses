package chat.network.objectprotocol;

import chat.network.dto.UserDTO;


public class LogoutRequest implements Request {
    private UserDTO user;

    public LogoutRequest(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
