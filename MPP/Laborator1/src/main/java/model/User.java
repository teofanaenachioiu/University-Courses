package model;

import java.io.Serializable;

public class User implements Serializable, HasID<String> {
    private String username;
    private String hash;
    private TipUser tip;

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
        this.username=s;
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


}
