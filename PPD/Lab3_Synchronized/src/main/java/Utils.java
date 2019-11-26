import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static boolean isSameContentInFile(String file_name1, String file_name2) throws IOException {

        try {
            FileReader file1 = new FileReader(file_name1);
            FileReader file2 = new FileReader(file_name2);

            BufferedReader inputReader1 = new BufferedReader(file1);
            BufferedReader inputReader2 = new BufferedReader(file2);

            List<String> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();

            String nextLine;
            while ((nextLine = inputReader1.readLine()) != null) {
                List<String> strings = Arrays.asList(nextLine.split(" *"));
                list1.addAll(strings);
            }
            list1.sort(String::compareTo);

            while ((nextLine = inputReader2.readLine()) != null) {
                List<String> strings = Arrays.asList(nextLine.split(" *"));
                list2.addAll(strings);
            }
            list2.sort(String::compareTo);

            return list1.equals(list2);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid files!");
        }
        return false;
    }

    private static int getRandomIntegerBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private static String getPolynomial(int minCoef, int maxCoef, int maxExp) {
        StringBuilder polynomial = new StringBuilder();
        for (int j = maxExp; j >= 0; j--) {
            polynomial.append(getRandomIntegerBetweenRange(minCoef, maxCoef))
                    .append(",")
                    .append(j)
                    .append("\n");
        }
        return String.valueOf(polynomial);
    }

    public static void generatePolynomials(int noPoly, int minCoef, int maxCoef, int maxExp) throws IOException {
        for (int i = 1; i <= noPoly; i++) {
            FileWriter fileWriter = new FileWriter("src/main/resources/polinom" + i + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String polynomial = getPolynomial(minCoef, maxCoef, maxExp);

            bufferedWriter.write(polynomial);
            bufferedWriter.close();
        }
    }
}