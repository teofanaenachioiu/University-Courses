package chat.network.objectprotocol;

import chat.network.dto.UserDTO;


public class FriendLoggedOutResponse implements UpdateResponse {
    private UserDTO friend;

    public FriendLoggedOutResponse(UserDTO friend) {
        this.friend = friend;
    }

    public UserDTO getFriend() {
        return friend;
    }
}
