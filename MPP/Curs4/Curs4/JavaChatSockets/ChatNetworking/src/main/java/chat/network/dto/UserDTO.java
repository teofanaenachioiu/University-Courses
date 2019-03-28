package chat.network.dto;

import java.io.Serializable;


public class UserDTO implements Serializable{
    private String id;
    private String passwd;

    public UserDTO(String id) {
        this(id,"");
    }

    public UserDTO(String id, String passwd) {
        this.id = id;
        this.passwd = passwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString(){
        return "UserDTO["+id+' '+passwd+"]";
    }
}
