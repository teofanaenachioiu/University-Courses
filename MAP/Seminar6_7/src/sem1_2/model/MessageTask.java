package sem1_2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {

    private String mesaj;
    private String from;
    private String to;
    private LocalDateTime data;

    public static DateTimeFormatter getDataFormatter() {
        return dataFormatter;
    }

    private static final DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:MM");

    public MessageTask(String taskId, String descriere, String mesaj, String from, String to, LocalDateTime data) {
        super(taskId, descriere);
        this.mesaj = mesaj;
        this.from = from;
        this.to = to;
        this.data = data;
    }



    @Override
    public void execute() {

        System.out.println(data.format(dataFormatter) + ": " + mesaj);
    }

    @Override
    public String toString() {
        return super.toString() + "mesaj=" + mesaj + "|" + "from=" + from + "data=" + data.format(dataFormatter);
    }
}
