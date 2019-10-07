import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileComparator {
    private static boolean isSameContentInFile(String file_name1, String file_name2) throws IOException {

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
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("File name1: ");
        String file_name1 = scanner.next();

        System.out.println("File name2: ");
        String file_name2 = scanner.next();

        boolean isSame = isSameContentInFile(file_name1,file_name2);
        System.out.println(isSame);
    }
}
