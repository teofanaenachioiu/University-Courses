package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteLog {
    private static String fileName = "out.txt";
    public static List<String> list = new ArrayList<>();
    public static void writeToFile(String cine, String ce){
        list.add(cine+" "+ce+" ");
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(fileName), true));
            br.append(cine + " " + ce + " ");
            br.newLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
