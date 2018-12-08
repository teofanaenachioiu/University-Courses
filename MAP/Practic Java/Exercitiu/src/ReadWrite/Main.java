package ReadWrite;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        //Cu buffer

       //Din fisier
        try (
            BufferedReader br=new BufferedReader(new FileReader("src/ReadWrite/fisier1.txt"));
            BufferedWriter bw=new BufferedWriter(new FileWriter("src/ReadWrite/fisier2.txt"))){
            String line;
            line=br.readLine();
            while(line!=null){
                   bw.write(line);
                   line=br.readLine();
                   bw.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // De la tastatura
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Scrie ceva: ");
        try {
            String linie=br.readLine();
            System.out.print("Raspuns: "+linie);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Buffered output/input stream
        try (BufferedInputStream bi=new BufferedInputStream(new FileInputStream("src/ReadWrite/fisier1.txt"));
             DataInputStream dataIn=new DataInputStream(bi);
             BufferedOutputStream bo=new BufferedOutputStream(new FileOutputStream("src/ReadWrite/fisier2.txt"));
             DataOutputStream dataOut=new DataOutputStream(bo);
        ) {
            Float nr=dataIn.readFloat();
            dataOut.writeFloat(nr);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
