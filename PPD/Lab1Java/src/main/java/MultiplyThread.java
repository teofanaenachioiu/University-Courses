public class MultiplyThread extends Thread {
    public int start;
    public int end;
    private BigNumber smallestNr;
    private BigNumber biggerNr;
    private BigNumber bigNumberResult;

    public MultiplyThread(int start, int end, BigNumber smallestNr, BigNumber biggerNr, BigNumber bigNumberResult) {
        this.start = start;
        this.end = end;
        this.smallestNr = smallestNr;
        this.biggerNr = biggerNr;
        this.bigNumberResult = bigNumberResult;
    }

    @Override
    public void run() {
        super.run();

        int maxSize = biggerNr.getSize();
        int carry, produs, deinmultit, inmultitor, i, j;
        for (i = start; i < end; i++) {
            inmultitor = smallestNr.getDigit(i);
            carry = 0;
            for (j = 0; j < maxSize; j++) {
                deinmultit = biggerNr.getDigit(j);
                produs = deinmultit * inmultitor + carry;
                carry = produs / 10 + bigNumberResult.addDigit(j + i, produs % 10);
            }
            if (carry != 0) {
                bigNumberResult.addDigit(j + i, carry % 10);
            }
        }
    }
}
