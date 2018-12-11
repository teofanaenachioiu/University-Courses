package data;

public class DataDTO {
    private String projectName;
    private Integer noOfUsers;
    private Integer noOfTickets;

    public DataDTO(String projectName, Integer noOfUsers, Integer noOfTickets) {
        this.projectName = projectName;
        this.noOfUsers = noOfUsers;
        this.noOfTickets = noOfTickets;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(Integer noOfUsers) {
        this.noOfUsers = noOfUsers;
    }

    public Integer getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(Integer noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    @Override
    public String toString() {
        return projectName+" "+noOfUsers+" "+noOfTickets;
    }
}
