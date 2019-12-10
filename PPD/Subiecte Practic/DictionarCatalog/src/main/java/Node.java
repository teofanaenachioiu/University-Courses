public class Node<TK , TV> {
    private TK key;
    private TV value;
    private Node next;


    public Node(TK key, TV value) {
        this.key = key;
        this.value = value;
    }

    public TK getKey() {
        return key;
    }

    public void setKey(TK key) {
        this.key = key;
    }

    public TV getValue() {
        return value;
    }

    public void setValue(TV value) {
        this.value = value;
    }

    public Node<TK,TV> getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
