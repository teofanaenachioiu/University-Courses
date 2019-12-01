import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DoublyLinkedList {
    private Node start;
    private int size;

    public DoublyLinkedList() {
        start = null;
        size = 0;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public int getSize() {
        return size;
    }

    public synchronized void insert(Monom val, boolean deleteImmediately) {
        Node newNode = new Node(val, null, null);
        Node crrNode, nextNode;
        boolean ins = false;
        if (start == null)
            start = newNode;
        else if (val.getExponent() > start.getData().getExponent()) {
            newNode.setLinkNext(start);
            start.setLinkPrev(newNode);
            start = newNode;
        } else if (val.getExponent() == start.getData().getExponent()) {
            start.addCoefficient(val.getCoefficient());

            if (start.isCoefficientZero() && deleteImmediately) {
                delete(start);
            }
            return;
        } else {
            crrNode = start;
            nextNode = start.getLinkNext();
            while (nextNode != null) {
                if (val.getExponent() < crrNode.getData().getExponent() && val.getExponent() > nextNode.getData().getExponent()) {
                    crrNode.setLinkNext(newNode);
                    newNode.setLinkPrev(crrNode);
                    newNode.setLinkNext(nextNode);
                    nextNode.setLinkPrev(newNode);
                    ins = true;
                    break;
                }

                if (val.getExponent() == nextNode.getData().getExponent()) {
                    nextNode.addCoefficient(val.getCoefficient());
                    ins = true;
                    size--;
                    if (nextNode.isCoefficientZero() && deleteImmediately) {
                        delete(nextNode);
                    }
                    break;
                }

                crrNode = nextNode;
                nextNode = nextNode.getLinkNext();
            }
            if (!ins) {
                crrNode.setLinkNext(newNode);
                newNode.setLinkPrev(crrNode);
            }
        }
        size++;
    }

    void prepare(int max) {
        Node node1 = new Node(new Monom(-1, max + 3), null, null);
        Node node2 = new Node(new Monom(-1, max + 2), null, null);
        Node node3 = new Node(new Monom(-1, max + 1), null, null);
        start = node1;
        start.setLinkNext(node2);
        node2.setLinkPrev(start);
        node2.setLinkNext(node3);
        node3.setLinkPrev(node2);
    }

    void broke() {
        start = start.getLinkNext().getLinkNext().getLinkNext();
        if (start != null) {
            start.setLinkPrev(null);
        }
    }

    void insertSync(Monom val, boolean deleteImmediately) {
        Node newNode = new Node(val, null, null);
        int valExponent = val.getExponent();

        Node currentNode, nextNode, prevNode;

        prevNode = start;
        currentNode = start.getLinkNext();
        nextNode = start.getLinkNext().getLinkNext();

        int crrExponent;
        int nextExponent;

        while (true) {
            synchronized (prevNode) {
                synchronized (currentNode) {
                    synchronized (nextNode) {
                        crrExponent = currentNode.getData().getExponent();
                        nextExponent = nextNode.getData().getExponent();

                        if (crrExponent > valExponent && valExponent > nextExponent) {
                            currentNode.setLinkNext(newNode);
                            newNode.setLinkPrev(currentNode);
                            newNode.setLinkNext(nextNode);
                            nextNode.setLinkPrev(newNode);
                            size++;
                            break;
                        }

                        if (valExponent == crrExponent) {
                            currentNode.addCoefficient(val.getCoefficient());
                            if (currentNode.isCoefficientZero() && deleteImmediately) {
                                delete(currentNode);
                            }
                            break;
                        }

                        if (nextNode.getLinkNext() == null) {
                            if (nextNode.getData().getExponent() == valExponent) {
                                nextNode.addCoefficient(val.getCoefficient());
                                if (nextNode.isCoefficientZero() && deleteImmediately) {
                                    delete(nextNode);
                                }
                            } else {
                                nextNode.setLinkNext(newNode);
                                newNode.setLinkPrev(nextNode);
                                size++;
                            }
                            break;
                        }

                        prevNode = currentNode;
                        currentNode = nextNode;
                        nextNode = nextNode.getLinkNext();
                    }
                }
            }
        }
    }

    void insertSync2(Monom val, boolean deleteImmediately) {
        Node newNode = new Node(val, null, null);

        int valExponent = val.getExponent();

        Node currentNode, nextNode, nextNextNode;

        synchronized (this) {
            if (start == null) {
                start = newNode;
                size++;
                return;
            }
        }

        synchronized (start) {
            if (valExponent > start.getData().getExponent()) {
                start.setLinkPrev(newNode);
                newNode.setLinkNext(start);
                start = newNode;
                size++;
                return;
            }

            if (start.getData().getExponent() == valExponent) {
                start.addCoefficient(val.getCoefficient());
                if (start.isCoefficientZero()) {
                    delete(start);
                }
                return;
            }

            if (start.getLinkNext() == null) {
                start.setLinkNext(newNode);
                newNode.setLinkPrev(start);
                size++;
                return;
            }

            currentNode = start;
            nextNode = start.getLinkNext();
        }

        int crrExponent;
        int nextExponent;

        while (true) {
            synchronized (currentNode) {
                synchronized (nextNode) {
                    if (nextNode.getLinkNext() != null) {
                        synchronized (nextNode.getLinkNext()) {
                            crrExponent = currentNode.getData().getExponent();
                            nextExponent = nextNode.getData().getExponent();

                            if (crrExponent > valExponent && valExponent > nextExponent) {
                                currentNode.setLinkNext(newNode);
                                newNode.setLinkPrev(currentNode);
                                newNode.setLinkNext(nextNode);
                                nextNode.setLinkPrev(newNode);
                                size++;
                                break;
                            }

                            if (valExponent == crrExponent) {
                                currentNode.addCoefficient(val.getCoefficient());
                                if (currentNode.isCoefficientZero() && deleteImmediately) {
                                    delete(currentNode);
                                }
                                break;
                            }

                            if (nextNode.getLinkNext() == null) {
                                if (nextNode.getData().getExponent() == valExponent) {
                                    nextNode.addCoefficient(val.getCoefficient());
                                    if (nextNode.isCoefficientZero() && deleteImmediately) {
                                        delete(nextNode);
                                    }
                                } else {
                                    nextNode.setLinkNext(newNode);
                                    newNode.setLinkPrev(nextNode);
                                    size++;
                                }
                                break;
                            }
                            currentNode = nextNode;
                            nextNode = nextNode.getLinkNext();
                            crrExponent = currentNode.getData().getExponent();
                            nextExponent = nextNode.getData().getExponent();

                            if (crrExponent > valExponent && valExponent > nextExponent) {
                                currentNode.setLinkNext(newNode);
                                newNode.setLinkPrev(currentNode);
                                newNode.setLinkNext(nextNode);
                                nextNode.setLinkPrev(newNode);
                                size++;
                                break;
                            }

                            if (valExponent == crrExponent) {
                                currentNode.addCoefficient(val.getCoefficient());
                                if (currentNode.isCoefficientZero() && deleteImmediately) {
                                    delete(currentNode);
                                }
                                break;
                            }

                            if (nextNode.getLinkNext() == null) {
                                if (nextNode.getData().getExponent() == valExponent) {
                                    nextNode.addCoefficient(val.getCoefficient());
                                    if (nextNode.isCoefficientZero() && deleteImmediately) {
                                        delete(nextNode);
                                    }
                                } else {
                                    nextNode.setLinkNext(newNode);
                                    newNode.setLinkPrev(nextNode);
                                    size++;
                                }
                                break;
                            }
                            currentNode = nextNode;
                            nextNode = nextNode.getLinkNext();
                        }
                    } else {
                        crrExponent = currentNode.getData().getExponent();
                        nextExponent = nextNode.getData().getExponent();

                        if (crrExponent > valExponent && valExponent > nextExponent) {
                            currentNode.setLinkNext(newNode);
                            newNode.setLinkPrev(currentNode);
                            newNode.setLinkNext(nextNode);
                            nextNode.setLinkPrev(newNode);
                            size++;
                            break;
                        }

                        if (valExponent == crrExponent) {
                            currentNode.addCoefficient(val.getCoefficient());
                            if (currentNode.isCoefficientZero() && deleteImmediately) {
                                delete(currentNode);
                            }
                            break;
                        }

                        if (nextNode.getLinkNext() == null) {
                            if (nextNode.getData().getExponent() == valExponent) {
                                nextNode.addCoefficient(val.getCoefficient());
                                if (nextNode.isCoefficientZero() && deleteImmediately) {
                                    delete(nextNode);
                                }
                            } else {
                                nextNode.setLinkNext(newNode);
                                newNode.setLinkPrev(nextNode);
                                size++;
                            }
                            break;
                        }
                        currentNode = nextNode;
                        nextNode = nextNode.getLinkNext();
                    }
                }
            }
        }
    }


    private void delete(Node node) {
        if (start == node) {
            start = start.getLinkNext();
            start.setLinkPrev(null);
        } else {
            Node ptr = start.getLinkNext();
            while (ptr != node) {
                ptr = ptr.getLinkNext();
            }

            if (ptr == null) {
                System.out.println("Node not found. I can't delete!! " + node.getData());
                return;
            }

            Node p = ptr.getLinkPrev();
            Node n = ptr.getLinkNext();

            p.setLinkNext(n);
            if (n != null) {
                n.setLinkPrev(p);
            }
        }
        size--;
    }

    public void display() {
        System.out.print("Doubly Linked List = ");
        if (size == 0) {
            System.out.print("empty\n");
            return;
        }
        if (start.getLinkNext() == null) {
            System.out.println(start.getData());
            return;
        }
        Node ptr;
        System.out.print(start.getData() + "\n");
        ptr = start.getLinkNext();
        while (ptr.getLinkNext() != null) {
            System.out.print(ptr.getData() + "\n");
            ptr = ptr.getLinkNext();
        }
        System.out.print(ptr.getData() + "\n");
    }

    void writeInFile(String filename) throws IOException {
        if (size != 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            StringBuilder str = new StringBuilder();

            if (start.getLinkNext() == null) {
                str.append(start.getData());
            } else {
                Node ptr;
                str.append(start.getData()).append("\n");
                ptr = start.getLinkNext();
                while (ptr.getLinkNext() != null) {
                    str.append(ptr.getData()).append("\n");
                    ptr = ptr.getLinkNext();
                }
                str.append(ptr.getData());

            }
            writer.write(str.toString());
            writer.close();
        }
    }

    void deleteZeros() {
        Node n = start;
        while (n != null) {
            if (n.getData().getCoefficient() == 0) {
                delete(n);
            }
            n = n.getLinkNext();
        }
    }
}