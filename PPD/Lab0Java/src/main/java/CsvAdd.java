
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvAdd {
    private static Scanner scanner = new Scanner(System.in);

    private static int readRow() {

        System.out.println("Row: ");
        String row_string = scanner.next();
        try {
            int row = Integer.parseInt(row_string);
            return row >= 0 ? row : -1;
        } catch (NumberFormatException ignored) {
        }
        return -1;
    }

    private static String readContent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Content: ");
        return scanner.nextLine();
    }

    private static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private static void shiftRows(FileReader fileReader, FileWriter fileWriter, String content, int destination_row) throws IOException {
        String line;
        int index = 0;

        BufferedReader br = new BufferedReader(fileReader);

        while ((line = br.readLine()) != null) {
            if (index == destination_row) {
                fileWriter.write(content + "\n");
            }
            fileWriter.append(line).append("\n");
            index++;
        }

        if(index<destination_row){
            fileWriter.append(content).append("\n");
        }

        fileReader.close();
        fileWriter.close();

        Path yourFile = Paths.get("D:/University-Courses/PPD/Lab0Java/src/main/java/tmp.csv");
        Files.move(yourFile, yourFile.resolveSibling("test.csv"), REPLACE_EXISTING);
    }

    public static void main(String[] args) throws IOException {
        File csvFile = new File("D:/University-Courses/PPD/Lab0Java/src/main/java/test.csv");
        File tmp = new File("D:/University-Courses/PPD/Lab0Java/src/main/java/tmp.csv");

        if (csvFile.exists() && csvFile.isFile()) {
            int destination_row = readRow();
            String content = escapeSpecialCharacters(readContent());
            FileWriter fileWriter =  new FileWriter(tmp,true );
            FileReader fileReader =  new FileReader(csvFile);
            shiftRows(fileReader,fileWriter, content, destination_row);
        }
    }
}
