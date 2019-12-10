import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static void readFromFile(List<Message> listaInsertUpdate, List<String> listaDelete) {
        File file = new File("src/main/resources/dictionar_problema1.csv");
        Scanner sc;
        try {
            sc = new Scanner(file);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] elems = line.split(",");
                if(MessageType.valueOf(elems[0]).equals(MessageType.INSERT_UPDATE)){
                    try {
                        listaInsertUpdate.add(new Message(elems[1], Integer.parseInt(elems[2])));
                    }catch (ArrayIndexOutOfBoundsException ex){
                        ex.printStackTrace();
                    }
                }
                else if(MessageType.valueOf(elems[0]).equals(MessageType.DELETE)){
                    listaDelete.add(elems[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static float computeAverage(List<Integer> list){
        if(list.size()==0) return 0;
        int sum=0;
        for (Integer integer : list) {
            sum += integer;
        }
        return (float) sum/list.size();
    }

}
