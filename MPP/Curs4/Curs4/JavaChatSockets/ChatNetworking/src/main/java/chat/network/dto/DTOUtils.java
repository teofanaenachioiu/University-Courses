package chat.network.dto;

import chat.model.Message;
import chat.model.User;


public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        String id=usdto.getId();
        String pass=usdto.getPasswd();
        return new User(id, pass);

    }
    public static UserDTO getDTO(User user){
        String id=user.getId();
        String pass=user.getPasswd();
        return new UserDTO(id, pass);
    }

    public static Message getFromDTO(MessageDTO mdto){
        User sender=new User(mdto.getSenderId());
        User receiver=new User(mdto.getReceiverId());
        String text=mdto.getText();
        return new Message(sender, text, receiver);

    }

    public static MessageDTO getDTO(Message message){
        String senderId=message.getSender().getId();
        String receiverId=message.getReceiver().getId();
        String txt=message.getText();
        return new MessageDTO(senderId, txt, receiverId);
    }

    public static UserDTO[] getDTO(User[] users){
        UserDTO[] frDTO=new UserDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    public static User[] getFromDTO(UserDTO[] users){
        User[] friends=new User[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }
}
