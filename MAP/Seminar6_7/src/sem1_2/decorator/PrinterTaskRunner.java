package sem1_2.decorator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterTaskRunner extends AbstractTaskRunner {
    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("HH:MM");

        super.executeOneTask();
        System.out.println("Task executat la ora: " + LocalDateTime.now().format(dataFormatter));
    }
}
