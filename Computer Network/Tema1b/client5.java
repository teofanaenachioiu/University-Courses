/*
Un client trimite unui server un numar.
Serverul va returna clientului sirul divizorilor acestui numar.
*/

import java.net.*;
import java.io.*;

public class client5 {

public static void main(String args[]) throws Exception {
        if(args.length>=2){
                try{
                int port=Integer.parseInt(args[1]);
                Socket c = new Socket(args[0],port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int a, b, s;
                System.out.print("Nr = ");
                a = Integer.parseInt(reader.readLine());

                DataInputStream socketIn = new DataInputStream(c.getInputStream());
                DataOutputStream socketOut = new DataOutputStream(c.getOutputStream());

                socketOut.writeShort(a);
                socketOut.flush();

                System.out.print("Divizorii numarului: ");
                while(true){
                        s = socketIn.readUnsignedShort();
                        if(s==0) break;
                        System.out.print(s+" ");
                }
                System.out.println();
                reader.close();
                c.close();
                }catch (NumberFormatException e) {
                        System.err.println("Argument" + args[1] + " must be an integer.");
                System.exit(1);
        }
        }
}
}
