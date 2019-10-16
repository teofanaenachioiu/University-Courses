public class ClassificationThread extends Thread {
    private int start;
    private int end;
    private BigNumber bigNumber1;
    private BigNumber bigNumber2;
    private int[] produceCarry;
    private int noThread;


    public ClassificationThread(int start, int end, BigNumber bigNumber1, BigNumber bigNumber2, int[] produceCarry, int noThread) {
        this.start = start;
        this.end = end;
        this.bigNumber1 = bigNumber1;
        this.bigNumber2 = bigNumber2;
        this.produceCarry = produceCarry;
        this.noThread = noThread;
    }

    @Override
    public void run() {
        super.run();
        int sum;
        for (int index = end - 1; index >= start; index--) {
            sum = bigNumber1.getDigit(index) + bigNumber2.getDigit(index);
            if (sum > 9) {
                this.produceCarry[noThread] = 1;
                break;
            } else if (sum < 9) {
                this.produceCarry[noThread] = 0;
                break;
            }else {
                this.produceCarry[noThread] = 2;
            }
        }

    }
}
