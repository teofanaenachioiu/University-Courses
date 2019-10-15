
public class BigNumber {
    private int[] digits;

    /**
     * Constructor
     * @param size numar intreg pozitiv: dimensiunea numarului
     */
    public BigNumber(int size) {
        this.digits = new int[size];
    }

    /**
     * Constructor
     * @param digits array de numere intregi >=0 si <=9: cifrele numarului
     */
    public BigNumber(int[] digits) {
        this.digits = digits;
    }

    /**
     * Lungimea numarului
     * @return numar intreg pozitiv
     */
    public int getSize() {
        return digits.length;
    }

    /**
     * Cifra de pe o anumita pozitie
     * @param index numar intreg pozitiv: pozitia cifrei
     * @return numar intrg >=0 si <=9: cifra de pe pozitia index sau 0 daca pozitia nu exista
     */
    public int getDigit(int index) {
        if (index < digits.length)
            return digits[index];
        return 0;
    }

    /**
     * Actializeaza cifra de pe o pozitie data
     * @param poz numar intreg pozitiv: pozitia pe care se doreste sa se seteze noua valoare a cifrei
     * @param digit numar intreg >=0 si <=9: noua cifra
     */
    public void setDigit(int poz, int digit) {
        digits[poz] = digit;
    }

    /**
     * Tipareste numarul
     */
    public void printBigNumber() {
        for (int digit : digits) {
            System.out.print(digit);
        }
        System.out.println();
    }

    /**
     * Adunarea secventiala a doua numere
     * @param otherNumber BigNumber: numarul ce va fi adaugat la numarul curent
     * @return BigNumber: suma numerelor
     */
    public BigNumber addSequential(BigNumber otherNumber) {
        BigNumber numberSum = createBigNumberSum(otherNumber.getSize());

        int maxPoz = Math.min(otherNumber.getSize(), this.getSize());
        int digitSum, carry = 0;

        for (int index = 0; index < maxPoz; index++) {
            digitSum = this.getDigit(index) + otherNumber.getDigit(index) + carry;
            numberSum.setDigit(index, digitSum % 10);
            carry = digitSum / 10;
        }

        // se determina numarul cel mai lung
        // adunarea continua doar cu numarul cel mai lung si carry
        BigNumber longestBigNumber = getLongestBigNumber(otherNumber, maxPoz);

        if (longestBigNumber != null) {
            while (maxPoz < longestBigNumber.getSize()) {
                digitSum = longestBigNumber.getDigit(maxPoz) + carry;
                numberSum.setDigit(maxPoz, digitSum % 10);
                carry = digitSum / 10;
                maxPoz++;
            }
        }

        // se seteaza carry-ul pe ultima pozitie
        numberSum.setDigit(maxPoz, carry);

        return numberSum;
    }

    /**
     * Adunarea paralela a doua numere
     * @param otherNumber BigNumber: numarul ce va fi adaugat la numarul curent
     * @param no_Threads numar intreg pozitiv: numarul de threaduri
     * @return BigNumber: suma numerelor
     * @throws InterruptedException
     */
    public BigNumber addParallel(BigNumber otherNumber, int no_Threads) throws InterruptedException {
        BigNumber numberSum = createBigNumberSum(otherNumber.getSize());

        int size = numberSum.getSize();
        int start = 0;
        int dim = size / no_Threads;
        int end = size / no_Threads;
        int rest = size % no_Threads;
        int[] carry = new int[no_Threads + 1];
        carry[0] = 0;

        Thread[] threads = new SimpleAddThread[no_Threads];

        for (int index = 0; index < no_Threads; index++) {
            carry[index + 1] = -1;

            if (rest > 0) {
                end++;
                rest--;
            }

            threads[index] = new SimpleAddThread(start, end, this, otherNumber, numberSum, index, carry);
            threads[index].start();

            start = end;
            end = end + dim;
        }

        for (int index = 0; index < no_Threads; index++) {
            threads[index].join();
        }

        // se seteaza carry-ul pe ultima pozitie
        if (carry[no_Threads] == 2 && carry[no_Threads - 1] == 1) {
            numberSum.setDigit(size, 1);
        }

        return numberSum;
    }

    /**
     * Determina numarul cel mai lung
     * @param otherNumber BigNumber: numarul cu care se compara numarul curent
     * @param minLength numar intreg pozitiv: lungimea celui mai scurt numar
     * @return BigNumber: numarul
     */
    private BigNumber getLongestBigNumber(BigNumber otherNumber, int minLength) {
        if (this.getSize() > minLength) {
            return this;
        } else if (otherNumber.getSize() > minLength) {
            return otherNumber;
        }
        return null;
    }

    /**
     * Creaza un obiect de tip BigNumber de dimensiunea celui mai mare numar + 1
     * @param otherSize numar intreg pozitiv: lungimea celui de-al doilea numar
     * @return BigNumber
     */
    private BigNumber createBigNumberSum(int otherSize) {
        int size = Math.max(this.getSize(), otherSize);
        return new BigNumber(size + 1);
    }

//    public BigNumber addParallel(BigNumber otherNumber, int no_Threads) throws InterruptedException {
//        BigNumber numberSum = computeSumNumber(otherNumber.getSize());
//
//        int size = numberSum.getSize();
//
//        int start = 0;
//        int dim = size / no_Threads;
//        int end = size / no_Threads;
//        int rest = size % no_Threads;
//
//        Thread[] threadsClass = new ClassificationThread[no_Threads];
//        Thread[] threads = new AddThread[no_Threads];
//
//        int[] carry = new int[no_Threads];
//
//        for (int index = 0; index < no_Threads; index++) {
//            if (rest > 0) {
//                end++;
//                rest--;
//            }
//
//            threadsClass[index] = new ClassificationThread(start, end, this, otherNumber, carry, index);
//            threadsClass[index].start();
//
//            threads[index] = new AddThread(start, end, this, otherNumber, numberSum, carry, index);
//            start = end;
//            end = end + dim;
//        }
//
//        for (int index = 0; index < no_Threads; index++) {
//            threadsClass[index].join();
//        }
//
//        for (int index = 1; index < no_Threads; index++) {
//            if (carry[index - 1] == 2 && carry[index] == 1) {
//                carry[index] = 2;
//            }
//        }
//        carry[0] = 0;
//
//        for (int index = 0; index < no_Threads; index++) {
//            if (carry[index] == 2 || carry[index] == 1) {
//                carry[index]--;
//            }
//            threads[index].start();
//        }
//
//        for (int index = 0; index < no_Threads; index++) {
//            threads[index].join();
//        }
//
//        if (carry[no_Threads - 1] == 1) {
//            numberSum.setDigit(size, 1);
//        }
//
//        return numberSum;
//    }
}