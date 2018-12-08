package Genericitate;

//Tablou cu elemente de tip Obiect

public class StackGen<E> {
    private E[] items=(E[]) new Object[20];
    private int vf=0;
    public void push(E elem){
        items[vf]=elem;
        vf++;
    }
    public E peek(){
        return (E)items[vf-1];
    }
}
