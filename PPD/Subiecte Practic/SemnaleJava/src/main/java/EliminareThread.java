public class EliminareThread extends Thread {
    private int bruiaj;
    private DoublyLinkedList linkedList;

    public EliminareThread(int bruiaj, DoublyLinkedList linkedList) {
        this.bruiaj = bruiaj;
        this.linkedList = linkedList;
    }

    @Override
    public void run() {
        super.run();
        linkedList.stergeMultipli(bruiaj);
    }
}
