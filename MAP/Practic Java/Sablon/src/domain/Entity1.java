package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Entity1 implements HasID<String> {
    String id;
    String nume;
    LocalDate data;

    public Entity1(String id, String nume, LocalDate data) {
        this.id = id;
        this.nume = nume;
        this.data = data;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return id+" "+nume+" "+data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity1)) return false;
        Entity1 entity1 = (Entity1) o;
        return Objects.equals(getID(), entity1.getID()) &&
                Objects.equals(getNume(), entity1.getNume()) &&
                Objects.equals(getData(), entity1.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getNume(), getData());
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String id) {
        this.id=id;
    }
}
