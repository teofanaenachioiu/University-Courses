import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileComparator {
    private static boolean isSameContentInFile(String file_name1, String file_name2) throws IOException {

        try {
            FileReader file1 = new FileReader(file_name1);
            FileReader file2 = new FileReader(file_name2);

            BufferedReader inputReader1 = new BufferedReader(file1);
            BufferedReader inputReader2 = new BufferedReader(file2);

            List<String> list1 = new LinkedList<>();
            List<String> list2 = new LinkedList<>();

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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("File name1: ");
        String file_name1 = scanner.next();

        System.out.println("File name2: ");
        String file_name2 = scanner.next();

        boolean isSame = isSameContentInFile(file_name1, file_name2);
        System.out.println(isSame);
    }
}
