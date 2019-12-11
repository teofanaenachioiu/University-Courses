public class Node {
    private Monom data;
    private Node next, prev;

    public Node(Monom d, Node n, Node p) {
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

    public void setData(Monom d) {
        data = d;
    }

    public Monom getData() {
        return data;
    }

    public void addCoefficient(int coefficient) {
        int oldCoefficient = this.data.getCoefficient();
        this.data.setCoefficient(oldCoefficient + coefficient);
    }

    public boolean isCoefficientZero() {
        return this.data.getCoefficient() == 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}