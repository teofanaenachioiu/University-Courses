
public class BigNumber {
    private int[] digits;

    /**
     * Constructor
     *
     * @param size numar intreg pozitiv: dimensiunea numarului
     */
    public BigNumber(int size) {
        this.digits = new int[size];
    }

    /**
     * Constructor
     *
     * @param digits array de numere intregi >=0 si <=9: cifrele numarului
     */
    public BigNumber(int[] digits) {
        this.digits = digits;
    }

    /**
     * Lungimea numarului
     *
     * @return numar intreg pozitiv
     */
    public int getSize() {
        return digits.length;
    }

    /**
     * Cifra de pe o anumita pozitie
     *
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
     *
     * @param poz   numar intreg pozitiv: pozitia pe care se doreste sa se seteze noua valoare a cifrei
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
     *
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
     * Adunarea paralela a doua numere (varianta 1)
     *
     * @param otherNumber BigNumber: numarul ce va fi adaugat la numarul curent
     * @param no_Threads  numar intreg pozitiv: numarul de threaduri
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
            numberSum.setDigit(numberSum.getSize(), 1);
        }

        return numberSum;
    }

    /**
     * Determina numarul cel mai lung
     *
     * @param otherNumber BigNumber: numarul cu care se compara numarul curent
     * @param minLength   numar intreg pozitiv: lungimea celui mai scurt numar
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
     *
     * @param otherSize numar intreg pozitiv: lungimea celui de-al doilea numar
     * @return BigNumber
     */
    private BigNumber createBigNumberSum(int otherSize) {
        int size = Math.max(this.getSize(), otherSize);
        return new BigNumber(size + 1);
    }

    /**
     * Adunarea paralela a doua numere (varianta 2)
     *
     * @param otherNumber BigNumber: numarul ce va fi adaugat la numarul curent
     * @param no_Threads  numar intreg pozitiv: numarul de threaduri
     * @return BigNumber: suma numerelor
     * @throws InterruptedException
     */
    public BigNumber addParallel22(BigNumber otherNumber, int no_Threads) throws InterruptedException {
        BigNumber numberSum = createBigNumberSum(otherNumber.getSize());

        int size = numberSum.getSize();

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

        for (int index = 0; index < no_Threads; index++) {
            if (index > 0 && carry[index] == 2 && carry[index - 1] == 1) {
                carry[index] = 1;
            }
            threads[index].start();
        }

        for (int index = 0; index < no_Threads; index++) {
            threads[index].join();
        }

        if (carry[no_Threads - 1] == 1) {
            numberSum.setDigit(size - 1, 1);
        }

        return numberSum;
    }



    BigNumber multiplySequentially(BigNumber otherNumber)  {
        BigNumber smallestNr = getSmallestNumber(otherNumber);
        BigNumber biggerNr = getBiggestNumber(otherNumber);

        int minSize = smallestNr.getSize();
        int maxSize = biggerNr.getSize();
        BigNumber result = new BigNumber(minSize + maxSize);


        int carry, produs, inmultitor, i,j;

        for (i = 0; i < minSize; i++) {
            inmultitor = smallestNr.getDigit(i);
            carry = 0;
            for (j = 0; j < maxSize; j++) {
                produs = biggerNr.getDigit(j) * inmultitor + carry;
                carry = produs / 10 + result.addDigit(j + i, produs % 10);
            }
            if (carry != 0) {
                result.addDigit(j + i, carry % 10);
            }
        }
        return result;
    }


    BigNumber multiplySequentiallyV2(BigNumber otherNumber) throws InterruptedException {
        BigNumber smallestNr = getSmallestNumber(otherNumber);
        BigNumber biggerNr = getBiggestNumber(otherNumber);

        int minSize = smallestNr.getSize();
        int maxSize = biggerNr.getSize();
        BigNumber result = new BigNumber(minSize + maxSize);


        int carry, produs, inmultitor;
        BigNumber partial;
        for (int i = 0; i < minSize; i++) {
            inmultitor = smallestNr.getDigit(i);
            partial = new BigNumber(maxSize + minSize);
            carry = 0;
            for (int j = 0; j < maxSize; j++) {
                produs = inmultitor * biggerNr.getDigit(j) + carry;
                partial.setDigit(i + j, produs%10);
                carry = produs/10;
            }
            result = result.addParallel22(partial, 4);
        }

        return result;
    }

    private BigNumber getSmallestNumber(BigNumber otherNumber) {
        return this.getSize() < otherNumber.getSize() ? this : otherNumber;
    }

    private BigNumber getBiggestNumber(BigNumber otherNumber) {
        return this.getSize() > otherNumber.getSize() ? this : otherNumber;
    }

    public int addDigit(int poz, int digit) {
        int sum = digits[poz] + digit;
        digits[poz] = sum % 10;
        return sum / 10;
    }

    public BigNumber multiplyParallel(BigNumber otherNumber, int no_Threads) throws InterruptedException {
        BigNumber smallestNr = getSmallestNumber(otherNumber);
        BigNumber biggerNr = getBiggestNumber(otherNumber);

        int minSize = smallestNr.getSize();
        int maxSize = biggerNr.getSize();

        int start = 0;
        int dim = minSize / no_Threads;
        int end = minSize / no_Threads;
        int rest = minSize % no_Threads;

        Thread[] threads = new MultiplyThread[no_Threads];
        BigNumber[] results = new BigNumber[no_Threads];

        for (int index = 0; index < no_Threads; index++) {
            if (rest > 0) {
                end++;
                rest--;
            }
            results[index] = new BigNumber(minSize + maxSize);

            threads[index] = new MultiplyThread(start, end, smallestNr, biggerNr, results[index]);
            threads[index].start();

            start = end;
            end = end + dim;
        }

        for (int index = 0; index < no_Threads; index++) {
            threads[index].join();
        }

//        int carry, sum;
//        carry = 0;
//        for (int i = 0; i <maxSize+minSize; i++) {
//            sum = carry;
//            for (int j = 1; j <no_Threads; j++) {
//                sum += results[j].getDigit(i);
//            }
//            carry = sum / 10 + results[0].addDigit(i, sum%10);
//        }

        for (int i = 1; i < no_Threads; i++) {
            results[0] = results[0].addParallel22(results[i], no_Threads);
        }
        return results[0];
    }
}