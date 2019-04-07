package chat.network.objectprotocol;

import chat.network.dto.UserDTO;


public class GetLoggedFriendsResponse implements Response{
    private UserDTO[] friends;

    public GetLoggedFriendsResponse(UserDTO[] friends) {
        this.friends = friends;
    }

    public UserDTO[] getFriends() {
        return friends;
    }
}
