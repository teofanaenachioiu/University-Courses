package tasks.repository;

/**
 * Created by grigo on 11/14/16.
 */
public interface HasId<ID> {
    ID getId();
    void setId(ID id);
}