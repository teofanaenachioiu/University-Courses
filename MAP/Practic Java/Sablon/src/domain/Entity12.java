package domain;

import javafx.util.Pair;

import java.util.Objects;

public class Entity12 implements HasID<Pair<Entity1,Entity2>>{
    Entity1 entity1;
    Entity2 entity2;
    Float valoare;

    public Entity12(Entity1 entity1, Entity2 entity2, Float valoare) {
        this.entity1 = entity1;
        this.entity2 = entity2;
        this.valoare = valoare;
    }

    public Entity1 getEntity1() {
        return entity1;
    }

    public void setEntity1(Entity1 entity1) {
        this.entity1 = entity1;
    }

    public Entity2 getEntity2() {
        return entity2;
    }

    public void setEntity2(Entity2 entity2) {
        this.entity2 = entity2;
    }

    public Float getValoare() {
        return valoare;
    }

    public void setValoare(Float valoare) {
        this.valoare = valoare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity12)) return false;
        Entity12 entity12 = (Entity12) o;
        return Objects.equals(getEntity1(), entity12.getEntity1()) &&
                Objects.equals(getEntity2(), entity12.getEntity2()) &&
                Objects.equals(getValoare(), entity12.getValoare());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntity1(), getEntity2(), getValoare());
    }

    @Override
    public String toString() {
        return entity1.toString()+" "+entity2.toString()+valoare;
    }

    @Override
    public Pair<Entity1, Entity2> getID() {
        return new Pair(entity1,entity2);
    }

    @Override
    public void setID(Pair<Entity1, Entity2> entity1Entity2Pair) {
        this.entity1=entity1Entity2Pair.getKey();
        this.entity2=entity1Entity2Pair.getValue();
    }
}
