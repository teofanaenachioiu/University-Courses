package runner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterTaskRunner extends  AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner runner) {
        super(runner);
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm")));
    }

    @Override
    public void executeAll() {
        while (this.taskRunner.hasTask())
            executeOneTask();
    }
}
