package chat.network.objectprotocol;

import chat.network.dto.MessageDTO;


public class SendMessageRequest implements Request{
    private MessageDTO message;

    public SendMessageRequest(MessageDTO message) {
        this.message = message;
    }

    public MessageDTO getMessage() {
        return message;
    }
}

