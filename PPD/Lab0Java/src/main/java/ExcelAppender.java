import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelAppender {
    public static void main(String[] args) throws IOException {
        File file = new File("testez.xlsx");

        FileInputStream fip = null;
        try {
            fip = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fip);

            if (file.isFile() && file.exists()) {
                System.out.println("Geeks.xlsx open");
            }
            else {
                System.out.println("Geeks.xlsx either not exist"
                        + " or can't open");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }


    }
}
