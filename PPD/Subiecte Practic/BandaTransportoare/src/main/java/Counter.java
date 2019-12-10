public class Counter {
    private int c = 0;
    public synchronized void increment() {
        c++;
    }
    public synchronized void decrement() {
        c--;
    }
    public synchronized int value() {
        return c;
    }
    public synchronized int get() {
        return c++;
    }
}
// il pui in clasa de thread si il declari static