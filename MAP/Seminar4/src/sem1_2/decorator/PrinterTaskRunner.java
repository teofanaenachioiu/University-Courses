package sem1_2.decorator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterTaskRunner extends AbstractTaskRunner {

    public PrinterTaskRunner(TaskRunner t) {
        super(t);
    }



    @Override
    public void executeOneTask() {
        super.executeOneTask();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
