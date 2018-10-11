package model;

public interface Container {
    Task remove();
    void add(Task task);
    int size();
    boolean isEmpty();
}
