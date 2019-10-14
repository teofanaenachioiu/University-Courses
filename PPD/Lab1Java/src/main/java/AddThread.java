public class AddThread extends Thread {
    public int start;
    public int end;
    private BigNumber bigNumber1;
    private BigNumber bigNumber2;
    private BigNumber bigNumberSum;
    private int noThread;
    private int[] carry;

    AddThread(int start, int end, BigNumber bigNumber1, BigNumber bigNumber2, BigNumber bigNumberSum, int[] carry, int noThread) {
        this.start = start;
        this.end = end;
        this.bigNumber1 = bigNumber1;
        this.bigNumber2 = bigNumber2;
        this.bigNumberSum = bigNumberSum;
        this.carry = carry;
        this.noThread = noThread;
    }

    @Override
    public void run() {
        super.run();
        int digitSum;
        int c = this.carry[noThread];
        for (int index = start; index < end; index++) {
            digitSum = bigNumber1.getDigit(index) + bigNumber2.getDigit(index) + c;
            bigNumberSum.setDigit(index, digitSum % 10);
            c = digitSum / 10;
        }
    }
}
