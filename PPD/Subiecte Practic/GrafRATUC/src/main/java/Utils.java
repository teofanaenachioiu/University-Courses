import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static List<Muchie> readFromFile(String filename) {
        List<Muchie> bruiaje = new ArrayList<>();
        File file = new File("src/main/resources/"+filename+".csv");
        Scanner sc;
        try {
            sc = new Scanner(file);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] elems = line.split(",");
                Muchie muchie = new Muchie(Integer.parseInt(elems[0]),Integer.parseInt(elems[1]), Integer.parseInt(elems[2]));
                bruiaje.add(muchie);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bruiaje;
    }

    public static List<Nod> readNoduriFromFile(String filename) {
        List<Nod> bruiaje = new ArrayList<>();
        File file = new File("src/main/resources/"+filename+".csv");
        Scanner sc;
        try {
            sc = new Scanner(file);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] elems = line.split(",");
                Nod nod = new Nod(Integer.parseInt(elems[0]),Integer.parseInt(elems[1]));
                bruiaje.add(nod);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bruiaje;
    }
}
