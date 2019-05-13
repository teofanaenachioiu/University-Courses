package model;

/**
 * Interfata HasID
 *
 * @param <ID> tipul id-ului
 */
public interface HasID<ID> {
    ID getID();

    void setID(ID id);
}