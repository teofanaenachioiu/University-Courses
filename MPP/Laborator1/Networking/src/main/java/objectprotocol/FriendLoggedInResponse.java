package chat.network.objectprotocol;

import chat.network.dto.UserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:33:37 PM
 */
public class FriendLoggedInResponse implements UpdateResponse{
    private UserDTO friend;

    public FriendLoggedInResponse(UserDTO friend) {
        this.friend = friend;
    }

    public UserDTO getFriend() {
        return friend;
    }
}
