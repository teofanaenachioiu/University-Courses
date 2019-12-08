public class Node {
    private int data;
    private Node next, prev;

    public Node(int d, Node n, Node p) {
        data = d;
        next = n;
        prev = p;
    }

    public void setLinkNext(Node n) {
        next = n;
    }

    public void setLinkPrev(Node p) {
        prev = p;
    }

    public Node getLinkNext() {
        return next;
    }

    public Node getLinkPrev() {
        return prev;
    }

    public void setData(int d) {
        data = d;
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
