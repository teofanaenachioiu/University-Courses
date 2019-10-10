public class AddThread extends Thread {
    private int start;
    private int end;
    private Vector vector1;
    private Vector vector2;
    private Vector vectorSuma;

    AddThread(int start, int end, Vector vector1, Vector vector2, Vector vectorSuma){
        this.start = start;
        this.end = end;
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.vectorSuma = vectorSuma;
    }

    @Override
    public void run() {
        super.run();
        for(int index = start; index<end; index++){
            vectorSuma.elements.set(index,  Math.sqrt(vector1.elements.get(index)) +  Math.sqrt(vector2.elements.get(index)));
        }
    }
}
