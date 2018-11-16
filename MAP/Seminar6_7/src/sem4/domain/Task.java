package sem4.domain;


public class Task implements HasID<String> {

    private String id;
    private String description;

    public Task(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String s) {
        id=s;
    }
    public void execute(){}

    @Override
    public String toString() {
        return "id=" + id +
                "|"+ "description=" + description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
