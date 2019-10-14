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

    public static void createNewFile(String file_name, int size, int min, int max) throws IOException {
        int no_digits;
        int digit;

        File file = new File(file_name);
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
}
