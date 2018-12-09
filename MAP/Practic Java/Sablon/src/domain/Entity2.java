package domain;

import java.util.Objects;

public class Entity2 implements HasID<Integer>{
    Integer id;
    String denumire;

    public Entity2(Integer id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity2)) return false;
        Entity2 entity2 = (Entity2) o;
        return Objects.equals(getID(), entity2.getID()) &&
                Objects.equals(getDenumire(), entity2.getDenumire());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getDenumire());
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return id.toString()+" "+denumire;
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }
}
