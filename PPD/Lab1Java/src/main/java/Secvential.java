import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Secvential {
    public static ArrayList<Integer> primulNumar = new ArrayList<>();
    public static ArrayList<Integer> alDoileaNumar = new ArrayList<>();
    public static ArrayList<Integer> rezultatProdus = new ArrayList<>();
    public static ArrayList<Integer> rezultatFinal = new ArrayList<>();
    public static Integer transport;

    public static void CitesteNumerele(String fileName) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String primul= "", alDoilea= "",line = "";
        int nr = 0;
        try {
            while ((line = reader.readLine()) != null) {
                nr++;
                if(nr==1)
                    primul = line;
                if (nr == 2) {
                    alDoilea =line;
                    break;
                }
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        int lungimePrimul = primul.length();
        int lungimeAlDoilea = alDoilea.length();
        for(int i=0;i<lungimePrimul;i++)
        {
             primulNumar.add(Character.getNumericValue(primul.charAt(i)));
        }
        for(int i=0;i<lungimeAlDoilea;i++)
        {
             alDoileaNumar.add(Character.getNumericValue(alDoilea.charAt(i)));
        }
    }

    public static void ScrieRezultat() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/alina/Desktop/" + "RezultatSecvential.txt"));
        while(rezultatFinal.get(rezultatFinal.size()-1)==0)
            rezultatFinal.remove(rezultatFinal.size()-1);
        String str = Arrays.toString(rezultatFinal.toArray());
        writer.write(str);
        writer.close();
    }

    public static void ScrieInExcel(long endTime, long startTime) {
        System.out.println("Execution time: "+(endTime-startTime));
        System.out.println("Primul numar avea "+ primulNumar.size()+" cifre");
        System.out.println("Al doilea numar avea "+ alDoileaNumar.size()+" cifre");
    }

    public static void SumaDeLaIndex(int index) {
        transport = 0;
        int size = rezultatProdus.size();
        for (int i = 0;i < size;i++)
        {
            int suma = rezultatProdus.get(i) + rezultatFinal.get(i+index)+ transport;
            transport = suma / 10;
            rezultatFinal.set(i+index,suma%10);
        }
        if(transport!=0) {
            for (int i = size; i < rezultatFinal.size(); i++) {
                int suma = rezultatFinal.get(i) + transport;
                transport= suma / 10;
                rezultatFinal.set(i, suma % 10);
            }
        }
    }

    public static void InmultireCuOCifra(int cifra) {
        transport = 0;
        rezultatProdus.clear();
        int nrCifreAlDoilea = alDoileaNumar.size();
        int nrCifrePrimul = primulNumar.size();
        if (nrCifrePrimul >= nrCifreAlDoilea)
        {
            for (int i = 0;i < nrCifrePrimul;i++)
            {
                int prod = primulNumar.get(i) * cifra + transport;
                transport = prod / 10;
                rezultatProdus.add(prod % 10);
            }
        }
        else
        {
            for (int i = 0;i < nrCifreAlDoilea;i++)
            {
                int prod = alDoileaNumar.get(i) * cifra + transport;
                transport = prod / 10;
                rezultatProdus.add(prod % 10);
            }
        }
        if(transport!=0) {
            rezultatProdus.add(transport);
        }
    }

    public static void Produs() {
        int nrCifreAlDoilea = alDoileaNumar.size();
        int nrCifrePrimul = primulNumar.size();
        for(int i=0;i<nrCifrePrimul+nrCifreAlDoilea+2;i++)
        {
            rezultatFinal.add(0);
        }
        if(nrCifrePrimul>nrCifreAlDoilea)
        {
            for(int i=0;i<nrCifreAlDoilea;i++)
            {
                InmultireCuOCifra(alDoileaNumar.get(i));
                SumaDeLaIndex(i);
            }
        }
        else
        {
            for(int i=0;i<nrCifrePrimul;i++)
            {
                InmultireCuOCifra(primulNumar.get(i));
                SumaDeLaIndex(i);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Utils.createNewFile("num.txt", 10, 100_000, 200_100);
        CitesteNumerele("num.txt");
        long startTime = System.currentTimeMillis();
        Produs();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
//        ScrieRezultat();
//       ScrieInExcel(endTime,startTime);
    }
}
