package Genericitate;

public class StackObj {
    private Object [] items;
    private int vf=0;

    public StackObj(){
        items=new Object[50];
    }

    public Object push(Object e){
        items[vf]=e;
        vf++;
        return e;
    }

    public Object peek(){
        return items[vf-1];
    }
}
