package utils;

import domain.Student;
import view.StudentController;

public class ControllerChangeEvent implements Event {
    private ChangeEventType type;
    private StudentController data, oldData;

    public ControllerChangeEvent(ChangeEventType type, StudentController data) {
        this.type = type;
        this.data = data;
    }
    public ControllerChangeEvent(ChangeEventType type, StudentController data, StudentController oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public StudentController getData() {
        return data;
    }

    public StudentController getOldData() {
        return oldData;
    }
}
