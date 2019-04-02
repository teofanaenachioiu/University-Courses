package domain;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String parola;

    public User(String username, String parola) {
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return username+"/"+parola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getParola(), user.getParola());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getParola());
    }
}
