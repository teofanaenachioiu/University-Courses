package domain;

/**
 * interface for an entity which have an id
 * @param <ID> type of entity
 */
public interface HasID<ID> {
    ID getID();
    void setID(ID id);
}