public class SimpleAddThread extends Thread {
    private int start;
    private int end;
    private BigNumber bigNumber1;
    private BigNumber bigNumber2;
    private BigNumber bigNumberSum;
    private int[] carry;
    private int noThread;

    /**
     * Constructor
     * @param start numar intreg pozitiv: pozitia de start
     * @param end numar intreg pozitiv: pozitia pana la care se va prelucra numarul
     * @param bigNumber1: numar de adunat
     * @param bigNumber2: numar de adunat
     * @param bigNumberSum: numarul in care se va retine suma
     * @param noThread: numarul tread-ului
     * @param carry: array de carry-uri
     *             array-ul poate avea valorile:
     *             -1:  inca nu s-a calculat carry-ul pe acest thread
     *             0: carry-ul este 0
     *             1: carry-ul este 1 (suma trebuie recalculata si se va incepe cu carry 1)
     *             2: thread-ul are "potential de carry" (portiunea de numar contine doar cifre de 9)
     *                  un carry venit de pe alta secventa va face ca si aceasta secventa
     *                  sa produca un carry egal cu 1
     */
    public SimpleAddThread(int start, int end, BigNumber bigNumber1, BigNumber bigNumber2, BigNumber bigNumberSum, int noThread, int[] carry) {
        this.start = start;
        this.end = end;
        this.bigNumber1 = bigNumber1;
        this.bigNumber2 = bigNumber2;
        this.bigNumberSum = bigNumberSum;
        this.noThread = noThread;
        this.carry = carry;
    }

    @Override
    public void run() {
        super.run();

        int digitSum, c = 0;
        boolean potentialCarry = true;

        for (int index = start; index < end; index++) {
            digitSum = bigNumber1.getDigit(index) + bigNumber2.getDigit(index) + c;
            bigNumberSum.setDigit(index, digitSum % 10);
            c = digitSum / 10;
            if (digitSum % 10 != 9) {
                potentialCarry = false;
            }
        }
        if (potentialCarry) {
            carry[noThread + 1] = 2;
        } else {
            carry[noThread + 1] = c;
        }

        waitUntilAllThreadsAreFinished();

        setCarry();

        if (carry[noThread] == 1) {
            c = 1;
            for (int index = start; index < end; index++) {
                digitSum = bigNumberSum.getDigit(index) + c;
                bigNumberSum.setDigit(index, digitSum % 10);
                c = digitSum / 10;
                if (c == 0) break;
            }
        }
    }

    /**
     * Se actualizeaza carry-urile pe thread-urile cu potential de carry
     *
     * Se verifica daca thread-ul are "potential de carry" (carry[no_thread]==2).
     * In caz afirmativ, valoarea carry-ului se actualizeaza
     * cu cel mai apropiat 0 sau 1, cautat in partea stanga
     */
    private void setCarry() {
        if (carry[noThread] == 2) {
            int index = noThread - 1;
            do {
                carry[noThread] = carry[index];
                index--;
            } while (carry[noThread] == 2);
        }
    }

    /**
     * Se verifica daca toate carry-urile au fost calculate (carry[index] != -1)
     * Valoarea lui carry[0] se neglijeaza, deoarece ea e mereu 0.
     */
    private void waitUntilAllThreadsAreFinished() {
        int index = 1;

        while (true) {
            while (index <= noThread) {
                if (carry[index] == -1) {
                    index = 1;
                } else {
                    index++;
                }
            }
            break;
        }
    }
}
