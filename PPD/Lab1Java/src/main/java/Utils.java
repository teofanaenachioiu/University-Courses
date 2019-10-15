import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    private static Random random = new Random();

    private static int getDigit(int index) {
        if (index == 0) {
            return random.nextInt(9) + 1;
        } else {
            return random.nextInt(10);
        }
    }

    public static void createNewFile(String fileName, int size, int min, int max) throws IOException {
        int no_digits;
        int digit;

        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);

        for (int i = 0; i < size; i++) {
            no_digits = random.nextInt((max - min) + 1) + min;
            for (int j = 0; j < no_digits; j++) {
                digit = getDigit(j);
                writer.append(String.valueOf(digit));
            }
            writer.append("\n");
        }
        writer.close();
    }

    public static List<BigNumber> readNumbersFromFile(String fileName) {
        List<BigNumber> numbers = new ArrayList<>();

        try {
            FileReader file = new FileReader(fileName);
            BufferedReader inputReader = new BufferedReader(file);

            String nextLine;
            while ((nextLine = inputReader.readLine()) != null) {
                List<String> strings = Arrays.asList(nextLine.split("[\\s]"));
                strings.forEach(str -> numbers.add(new BigNumber(getDigits(str))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    private static int[] getDigits(String string) {
        int[] digits = new int[string.length()];
        for (int index = 0; index < string.length(); index++) {
            digits[index] = Integer.parseInt(string.substring(index, index + 1));
        }
        return digits;
    }

    public static void writeNumberInFile(BigNumber bigNumber, String fileName) throws IOException {
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);

        int size = bigNumber.getSize();
        for (int i = 0; i < size; i++) {
            writer.append(String.valueOf(bigNumber.getDigit(i)));
        }
        writer.close();
    }

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
}
