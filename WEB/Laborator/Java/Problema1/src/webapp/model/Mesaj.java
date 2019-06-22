package webapp.model;

import java.util.Objects;

public class Mesaj {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String username;
    private String msg;

    public Mesaj(Integer id, String username, String msg) {
        this.id = id;
        this.username = username;
        this.msg = msg;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mesaj)) return false;
        Mesaj mesaj = (Mesaj) o;
        return Objects.equals(getId(), mesaj.getId()) &&
                Objects.equals(getUsername(), mesaj.getUsername()) &&
                Objects.equals(getMsg(), mesaj.getMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getMsg());
    }
}
