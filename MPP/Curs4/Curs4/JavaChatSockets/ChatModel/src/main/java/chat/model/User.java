package chat.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;


public class User implements Comparable<User>, Serializable,Identifiable<String>{
    private String username, passwd, name;
    private Set<User> friends;


    public User() {
        this("");
    }

    public User(String username) {
        this(username,"","");
    }

    public User(String username, String passwd) {
        this(username,passwd,"");
    }

    public User(String username, String passwd, String name) {
        this.username = username;
        this.passwd = passwd;
        this.name = name;
        friends=new TreeSet<User>();
    }

    public String getPasswd() {
        return passwd;
    }

    public String getId() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        username = id;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }


   public Iterable<User> getFriends(){
       return friends;
   }
    public void addFriend(User u){
        friends.add(u);

    }
    public void removeFriend(User u){
        friends.add(u);
    }


    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    public boolean equals(Object obj) {
        if (obj instanceof User){
            User u=(User)obj;
            return username.equals(u.username);
        }
        return false;
    }

    @Override
    public String toString() {
     /*  StringBuilder friendsString=new StringBuilder();
        for (User user : friends){
            friendsString.append(user.getName());
            friendsString.append(", ");
        }*/
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
               // ", friends='"+friendsString+'\''+
                '}';
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
