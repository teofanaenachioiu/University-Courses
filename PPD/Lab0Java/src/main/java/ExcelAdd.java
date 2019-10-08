import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Scanner;

public class ExcelAdd {
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

    public static void main(String[] args) throws IOException {
        int destination_row = readRow();

        File file = new File("D:/University-Courses/PPD/Lab0Java/src/main/java/testez.xlsx");

        FileInputStream fip = null;
        try {
            if (file.isFile() && file.exists()) {
                System.out.println("testez.xlsx open");

                fip = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fip);

                Sheet sheet = workbook.getSheetAt(0);

                if(destination_row==-1){
                    destination_row =sheet.getLastRowNum()+1;
                }

                Row newRow = sheet.getRow(destination_row);
                if (newRow != null) {
                    sheet.shiftRows(destination_row, sheet.getLastRowNum(), 1);
                }
                newRow = sheet.createRow(destination_row);

                Cell cell = newRow.createCell(0);
                cell.setCellValue(readContent());

                FileOutputStream out = new FileOutputStream("D:/University-Courses/PPD/Lab0Java/src/main/java/testez.xlsx");
                workbook.write(out);
                workbook.close();
                out.close();
            } else {
                System.out.println("file not exist or can't open");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
