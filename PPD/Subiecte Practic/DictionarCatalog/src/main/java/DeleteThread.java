import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeleteThread extends Thread {
    private DictionarN1 dictionar;
    private List<String> messageList;

    public DeleteThread(DictionarN1 dictionar, List<String> listaMessage) {
        this.dictionar = dictionar;
        this.messageList = listaMessage;
    }


    @Override
    public void run() {
        super.run();

        int index = 0;

        while (index < messageList.size()) {
            dictionar.deleteValue(messageList.get(index));
            index++;
        }
        FileLogger.write("From delete thread ");
    }
}
