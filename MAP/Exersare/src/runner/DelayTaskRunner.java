package runner;

public class DelayTaskRunner extends AbstractTaskRunner {
    public DelayTaskRunner(TaskRunner runner) {
        super(runner);
    }

    @Override
    public void executeOneTask() {
        try {
            Thread.sleep(3000);
            taskRunner.executeOneTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void executeAll() {
        while (taskRunner.hasTask()) executeOneTask();
    }

}
