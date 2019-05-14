package model;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User implements Serializable, HasID<String> {
    @Id
    private String username;
    private String hash;
    @Enumerated(EnumType.STRING)
    private TipUser tip;

    public User() {
    }

    public User(String username, String hash) {
        this.username = username;
        this.hash = hash;
        this.tip = TipUser.ADMIN;
    }

    public User(String username, String hash, TipUser type) {
        this.username = username;
        this.hash = hash;
        this.tip = type;
    }

    @Override
    public String getID() {
        return username;
    }

    @Override
    public void setID(String s) {
        this.username = s;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public TipUser getTip() {
        return tip;
    }

    public void setTip(TipUser tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return username + " " + hash + " " + tip;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(getHash(), user.getHash()) &&
                getTip() == user.getTip();
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, getHash(), getTip());
    }
}
