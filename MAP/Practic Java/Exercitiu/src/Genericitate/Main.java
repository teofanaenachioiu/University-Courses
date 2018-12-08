package Genericitate;

public class Main {
    public static void main(String[] args) {
        StackArr stack=new StackArr(Integer.class);

        stack.push(100);
        System.out.println(stack.peek());
        stack.push(101);
        System.out.println(stack.peek());
    }
}
