package Genericitate;

import java.lang.reflect.Array;

//Metoda Array.newInstance

public class StackArr<E> {
    E[] items;
    int vf=0;
    public StackArr(Class tip){
        items=(E[]) Array.newInstance(tip,20);
    }
    public void push(E elem){
        items[vf]=elem;
        vf++;
    }
    public E peek(){
        return items[vf-1];
    }
}
