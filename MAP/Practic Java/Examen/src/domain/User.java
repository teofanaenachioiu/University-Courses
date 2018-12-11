package domain;

public class User implements HasID<String> {
    private String userId;
    private Enum role;
    private String projectName;

    public User(String userId,Enum role, String projectName) {
        this.userId=userId;
        this.role = role;
        this.projectName = projectName;
    }

    @Override
    public String getID() {
        return this.userId;
    }

    @Override
    public void setID(String s) {
        this.userId=s;
    }

    public Enum getRole() {
        return role;
    }

    public void setRole(Enum role) {
        this.role = role;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return  userId +"," + role +"," + projectName;
    }
}
