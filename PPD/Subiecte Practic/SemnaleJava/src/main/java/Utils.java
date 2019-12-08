import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static void readSemnaleFromFile(DoublyLinkedList linkedList) {
        File file = new File("D:\\University-Courses\\PPD\\Subiecte Practic\\SemnaleJava\\src\\main\\resources\\semnale_in.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
            String line;
            while (sc.hasNextLine() && Integer.parseInt(line = sc.nextLine()) > 0) {
                linkedList.insert(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> readBruiajFromFile() {
        List<Integer> bruiaje = new ArrayList<>();
        File file = new File("D:\\University-Courses\\PPD\\Subiecte Practic\\SemnaleJava\\src\\main\\resources\\bruiaj.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                bruiaje.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bruiaje;
    }

    public static List<Interval> readIntervaleFromFile(int nr_intervale) {
        List<Interval> intervale = new ArrayList<>();
        File file = new File("D:\\University-Courses\\PPD\\Subiecte Practic\\SemnaleJava\\src\\main\\resources\\interval.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] elems = line.split(",");
                Interval interval = new Interval(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]));
                intervale.add(interval);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return intervale;
    }

    private static int getRandomIntegerBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static void generareSemnale(int n) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/semnale_in.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        StringBuilder numbers = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int number = getRandomIntegerBetweenRange(9, 10000);
            if (number == 9) number = -1;

            numbers.append(number)
                    .append("\n");
        }

        bufferedWriter.write(String.valueOf(numbers));
        bufferedWriter.close();
    }

    public static void generareBruiaje(int n) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/bruiaj.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        StringBuilder numbers = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int number = getRandomIntegerBetweenRange(9, 10000);

            numbers.append(number)
                    .append("\n");
        }

        bufferedWriter.write(String.valueOf(numbers));
        bufferedWriter.close();
    }

    public static void generareIntervale(int nr_intervale) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/interval.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        StringBuilder numbers = new StringBuilder();

        int cate = 10000 / nr_intervale;

        for (int i = 0; i < nr_intervale; i++) {
            numbers.append(i*cate)
                    .append(",")
                    .append((i+1)*cate)
                    .append("\n");
        }

        bufferedWriter.write(String.valueOf(numbers));
        bufferedWriter.close();
    }
}
