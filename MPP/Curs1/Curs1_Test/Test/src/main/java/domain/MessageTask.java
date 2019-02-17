package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {

    private String message;
    private  String from;
    private String to;
    private LocalDateTime date;

    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MessageTask(String taskId, String desc, String message, String from, String to, LocalDateTime d) {
        super(taskId, desc);
        this.message=message;
        this.from=from;
        this.to=to;
        this.date= d;
    }

    public String getMessage()
    {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return getDateAsString();
    }

    @Override
    public void execute() {
        System.out.println(message);
    }

    @Override
    public String toString() {
        return super.toString()+";"+ message + ";" +from +";"+to+";"+date.format(dateFormatter);
    }

    public String getDateAsString(){return date.format(dateFormatter);}
}