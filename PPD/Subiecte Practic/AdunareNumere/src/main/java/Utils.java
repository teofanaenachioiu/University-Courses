public class Utils {

    private static int getRandomIntegerBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static Numar[] generateNumber(int n) {
        Numar[] numere = new Numar[n];

        for (int i = 0; i < n; i++) {
            numere[i] = new Numar(getRandomIntegerBetweenRange(0, 10));
        }

        return numere;
    }

    public static int addSeq(Numar[] numere, int n) {
        int suma = 0;
        for (int i = 0; i < n; i++) {
            suma += numere[i].getNumber();
        }
        return suma;
    }
}
