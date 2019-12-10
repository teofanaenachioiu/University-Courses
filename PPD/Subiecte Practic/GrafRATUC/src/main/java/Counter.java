public class Counter {
    private Integer c = 0;
    public synchronized void increment() {
        c++;
    }
    public synchronized void decrement() {
        c--;
    }
    public synchronized Integer value() {
        return c;
    }
    public synchronized Integer get() {
        return c++;
    }

}
// il pui in clasa de thread si il declari static