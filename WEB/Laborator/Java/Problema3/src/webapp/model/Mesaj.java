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
    private String msg;

    public Mesaj(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
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
                Objects.equals(getMsg(), mesaj.getMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMsg());
    }
}
