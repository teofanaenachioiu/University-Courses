import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class FileCreator {
    private static Random random = new Random();

    private static void writeInFile(FileWriter writer, int size, int min, int max) throws IOException {
        int number;
        for (int i = 0; i < size; i++) {
            number = random.nextInt((max - min) + 1) + min;
            writer.append(String.valueOf(number)).append("\n");
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("File name: ");
        String file_name = scanner.next();

        System.out.println("Size: ");
        int size = scanner.nextInt();

        System.out.println("Minimum: ");
        int min = scanner.nextInt();

        System.out.println("Maximum: ");
        int max = scanner.nextInt();

        createNewFile(file_name,size,min,max);
    }

    private static void createNewFile(String file_name, int size, int min, int max) throws IOException {
        File file = new File(file_name);

        if (file.createNewFile()) {
            System.out.println("File was created!");
        } else {
            System.out.println("File already exists and it was override!");
        }

        FileWriter writer = new FileWriter(file);
        writeInFile(writer, size, min, max);
    }
}
