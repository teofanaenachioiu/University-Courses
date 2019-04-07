package chat.network.objectprotocol;

import chat.network.dto.MessageDTO;


public class NewMessageResponse implements UpdateResponse{
    private MessageDTO message;

    public NewMessageResponse(MessageDTO message) {
        this.message = message;
    }

    public MessageDTO getMessage() {
        return message;
    }
}
