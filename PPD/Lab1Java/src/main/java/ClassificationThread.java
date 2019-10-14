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
        boolean CanProduceCarry = true;
        int carry = 0, sum;
        for (int index = end - 1; index >= start; index--) {
            sum = bigNumber1.getDigit(index) + bigNumber2.getDigit(index) + carry;
            carry = sum / 10;
            if (carry == 1) {
                break;
            }
            if (sum % 10 != 9) {
                CanProduceCarry = false;
                break;
            }
        }
        if (carry == 1) {
            this.produceCarry[noThread] = 2;
        } else if (CanProduceCarry) {
            this.produceCarry[noThread] = 1;
        } else {
            this.produceCarry[noThread] = 0;
        }
    }
}
