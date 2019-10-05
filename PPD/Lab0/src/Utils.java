import java.io.*;
import java.util.Random;

public class Utils {
    private static Random random = new Random();

    private static void writeInFile(FileWriter writer, int size, int min, int max) throws IOException {
        int number;
        for (int i = 0; i < size; i++) {
            number = random.nextInt((max - min) + 1) + min;
            writer.append(String.valueOf(number)).append(" ");
        }
        writer.close();
    }

    public static void createNewFile(String file_name, int size, int min, int max) throws IOException {
        File file = new File(file_name);

        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists and it was override!");
        }

        FileWriter writer = new FileWriter(file);
        writeInFile(writer, size, min, max);
    }

    public static boolean isSameContentInFile(String file_name1, String file_name2) throws IOException {

        try {
            FileReader file1 = new FileReader(file_name1);
            FileReader file2 = new FileReader(file_name2);

            BufferedReader inputReader1 = new BufferedReader(file1);
            BufferedReader inputReader2 = new BufferedReader(file2);

            String nextLine1;
            String nextLine2;
            while ((nextLine1 = inputReader1.readLine()) != null && (nextLine2 = inputReader2.readLine()) != null) {
                if (!nextLine1.equals(nextLine2)) {
                    return false;
                }
            }
            if (inputReader1.readLine() != null || inputReader2.readLine() != null) {
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid files!");
            return false;
        }
        return true;
    }
}
