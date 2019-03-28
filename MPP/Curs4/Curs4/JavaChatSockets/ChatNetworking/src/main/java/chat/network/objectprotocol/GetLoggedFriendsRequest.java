package chat.network.objectprotocol;

import chat.network.dto.UserDTO;


public class GetLoggedFriendsRequest implements Request {
    private UserDTO user;

    public GetLoggedFriendsRequest(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}

