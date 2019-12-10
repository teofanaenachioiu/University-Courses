public class Node {
    private TransactionType transactionType;
    private Node next;

    public Node(TransactionType transactionType, Node next) {
        this.transactionType = transactionType;
        this.next = next;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "transactionType=" + transactionType +
                '}';
    }
}
