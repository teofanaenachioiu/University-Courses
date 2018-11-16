package semGUI;

import javafx.beans.property.SimpleStringProperty;

public class Person {
//    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName1=null;
//    private final SimpleStringProperty email;

    private  String firstName;
    private  String lastName;
    private  String email;

//    Person(String fName, String lName, String email) {
////        this.firstName = new SimpleStringProperty(fName);
////        this.lastName = new SimpleStringProperty(lName);
////        this.email = new SimpleStringProperty(email);
////    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //    public String getFirstName() {
//        return firstName.get();
//    }
//    public void setFirstName(String fName) {
//        firstName.set(fName);
//    }
//
//    public String getLastName() {
//        return lastName.get();
//    }
//    public void setLastName(String fName) {
//        lastName.set(fName);
//    }
//
//    public String getEmail() {
//        return email.get();
//    }
//    public void setEmail(String fName) {
//        email.set(fName);
//    }

}