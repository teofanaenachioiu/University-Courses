
public class BigNumber {
    private int[] digits;

    public BigNumber(int size) {
        this.digits = new int[size];
    }

    public BigNumber(int[] digits) {
        this.digits = digits;
    }

    public int getSize() {
        return digits.length;
    }

    public int getDigit(int index) {
        if (index < digits.length)
            return digits[index];
        return 0;
    }

    public void setDigit(int poz, int digit) {
        digits[poz] = digit;
    }

    public void printNumber() {
        for (int digit : digits) {
            System.out.print(digit);
        }
        System.out.println();
    }

    public BigNumber addSequential(BigNumber otherNumber) {
        int maxLengthNumber = Math.max(otherNumber.getSize(), this.getSize());
        BigNumber numberSum = new BigNumber(maxLengthNumber + 1);

        int minLengthNumber = Math.min(otherNumber.getSize(), this.getSize());

        int carry = 0;
        int digitSum = 0;

        for (int index = 0; index < minLengthNumber; index++) {
            digitSum = this.getDigit(index) + otherNumber.getDigit(index) + carry;
            numberSum.setDigit(index, digitSum % 10);
            carry = digitSum / 10;
        }

        BigNumber bigNumberRest = getBigNumberRest(otherNumber, minLengthNumber);

        if (bigNumberRest != null) {
            while (bigNumberRest.getSize() > minLengthNumber) {
                digitSum = bigNumberRest.getDigit(minLengthNumber) + carry;
                numberSum.setDigit(minLengthNumber, digitSum % 10);
                carry = digitSum / 10;
                minLengthNumber++;
            }
        }

        if (carry > 0) {
            numberSum.setDigit(minLengthNumber, carry);
        } else {
            numberSum.digits[minLengthNumber] = 0;
        }
        return numberSum;
    }

    public BigNumber addParallel(BigNumber otherNumber, int no_Threads) throws InterruptedException {
        int size = Math.max(this.getSize(), otherNumber.getSize());

        BigNumber numberSum = new BigNumber(size + 1);

        int start = 0;
        int dim = size / no_Threads;
        int end = size / no_Threads;
        int rest = size % no_Threads;

        Thread[] threadsClass = new ClassificationThread[no_Threads];
        Thread[] threads = new AddThread[no_Threads];
        int[] carry = new int[no_Threads];

        for (int index = 0; index < no_Threads; index++) {
            if (rest > 0) {
                end++;
                rest--;
            }

            threadsClass[index] = new ClassificationThread(start, end, this, otherNumber, carry, index);
            threadsClass[index].start();

            threads[index] = new AddThread(start, end, this, otherNumber, numberSum, carry, index);
            start = end;
            end = end + dim;
        }

        for (int index = 0; index < no_Threads; index++) {
            threadsClass[index].join();
        }

        for (int index = 1; index < no_Threads; index++) {
            if (carry[index - 1] == 2 && carry[index] == 1) {
                carry[index] = 2;
            }
        }
        carry[0] = 0;

        for (int index = 0; index < no_Threads; index++) {
            if (carry[index] == 2 || carry[index] == 1) carry[index]--;

            threads[index].start();
        }

        for (int index = 0; index < no_Threads; index++) {
            threads[index].join();
        }

        if (carry[no_Threads - 1] == 1) {
            numberSum.setDigit(size, 1);
        }

        return numberSum;
    }

    private BigNumber getBigNumberRest(BigNumber otherNumber, int minLength) {
        if (this.getSize() > minLength) {
            return this;
        } else if (otherNumber.getSize() > minLength) {
            return otherNumber;
        }

        return null;
    }
}