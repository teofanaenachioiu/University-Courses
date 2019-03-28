package chat.client.gui;

import chat.model.Message;
import chat.model.User;
import chat.services.ChatException;
import chat.services.IChatObserver;
import chat.services.IChatServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController  implements Initializable,IChatObserver {
    @FXML
    ListView<String> friendsList;

    @FXML
    TableView<User> friendsTable;

    @FXML
    TextArea msgTxt;

    @FXML
     TextArea rcvMsgTxt;

    ObservableList<String> friends = FXCollections.observableArrayList();

    private IChatServer server;
    private User user;

    public ChatController() {
        //this.server = server;
        System.out.println("Constructor ChatController");

    }


    public ChatController(IChatServer server) {
        this.server = server;
        System.out.println("constructor ChatController cu server param");

    }

    public void setServer(IChatServer s) {
        server = s;
    }


    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("INIT : am in lista prieteni " + friends.size());

        System.out.println("END INIT!!!!!!!!!");
    }


    public void login(String id, String pass) throws ChatException {
        User userL = new User(id, pass, "");
        server.login(userL, this);
        user = userL;
        System.out.println("Autentificarea e ok!!!");

    }




    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void handleSendMessage(ActionEvent actionEvent) {
        int index = friendsTable.getSelectionModel().getSelectedIndex();
        if (index<0) Util.showWarning("Message has no specified receiver","Please select a friend from the list");

        String msg = msgTxt.getText();

        if (msg.isEmpty()) Util.showWarning("Message empty","Please fill in the message field before sending");
        try {
            sendMessage(index, msg);
            rcvMsgTxt.appendText("[me]: "+msg+"\n");
            msgTxt.clear();

        } catch (ChatException e) {
            Util.showWarning("Communication error","Your server probably closed connection");
        }
    }

     void logout() {
        try {
            server.logout(user, this);
        } catch (ChatException e) {
            System.out.println("Logout error " + e);
        }

    }


    public void sendMessage(int indexFriend, String txtMsg) throws ChatException {
        User sender=new User(user.getId());
        User receiver=friendsTable.getItems().get(indexFriend);
        Message message=new Message(sender,txtMsg,receiver);
        server.sendMessage(message);
    }

    public void messageReceived(Message message) throws ChatException {
        rcvMsgTxt.appendText(message.getSender().getId()+": "+message.getText()+"\n");
    }


    public void sendMessageToAll(String txtMsg) throws ChatException {

    }

    public void friendLoggedIn(User friend) throws ChatException {

        friendsTable.getItems().add(friend);
        System.out.println("Friends logged in " + friend.getName());


    }

    public void friendLoggedOut(User friend) throws ChatException {
        friendsTable.getItems().remove(friend);
    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setLoggedFriends() {
        try {
            User[] lFr = server.getLoggedFriends(user);
            friendsTable.getItems().clear();
            for (User u : lFr) {
                friendsTable.getItems().add(u);
            }
            if(friendsTable.getItems().size()>0)
               friendsTable.getSelectionModel().select(0);

        } catch (ChatException e) {
            e.printStackTrace();
        }
    }
}
