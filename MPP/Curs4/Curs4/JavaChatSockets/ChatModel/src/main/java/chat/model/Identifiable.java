package chat.model;


public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
