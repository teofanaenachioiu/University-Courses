import org.junit.Test;

public class DoublyLinkedListTest {

    @Test
    public void isEmpty() {
        DoublyLinkedList linkedList = new DoublyLinkedList();
        assert (linkedList.isEmpty());
        linkedList.insert(new Monom(5, 100));
        assert (!linkedList.isEmpty());
    }

    @Test
    public void getSize() {
        DoublyLinkedList linkedList = new DoublyLinkedList();
        assert (linkedList.getSize() == 0);
        linkedList.insert(new Monom(5, 100));
        assert (linkedList.getSize() == 1);
        assert (linkedList.getSize() != 2);
    }

    @Test
    public void insert() {
        DoublyLinkedList linkedList = new DoublyLinkedList();

        linkedList.insert(new Monom(1,100));
        assert (linkedList.getSize() == 1);

        linkedList.display();

        linkedList.insert(new Monom(1,99));
        assert (linkedList.getSize() == 2);

        linkedList.display();

        linkedList.insert(new Monom(1,99));
        assert (linkedList.getSize() == 2);

        linkedList.display();

        linkedList.insert(new Monom(-2,99));
        assert (linkedList.getSize() == 1);

        linkedList.display();
    }
}
