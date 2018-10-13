/*
Un client trimite unui server un sir de caractere si un caracter.
Serverul va returna clientului toate pozitiile
pe care caracterul primit se regaseste in sir.
*/

import java.net.*;
import java.io.*;

public class Client6{

public static void main(String args[]){
        String SERVER_ADDRESS=args[0];
        int SERVER_PORT=Integer.parseInt(args[1]);
        Socket socket=null;
        BufferedReader reader = null;
        try {
                reader = new BufferedReader(new InputStreamReader(System.in));
                socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
                System.out.print("Sir: ");
                String sir = reader.readLine();
                System.out.print("Caracter: ");
                char ch = (char)reader.read();
                writeToSocket(sir,ch,socket);
                readFromSocket(socket);
//              System.out.println(sir.length());
//              System.out.println(ch);
        } catch (IOException e) {
                System.err.println("Cautgh exception " + e.getMessage());
        } finally {
                closeStreams(socket,reader);
        }
}
private static void writeToSocket(String sir, char ch,Socket s) throws IOException{
        OutputStream os = s.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(ch);
        bw.write(sir);
        bw.flush();
}

private static void readFromSocket(Socket so) throws IOException{
        int poz;
        DataInputStream socketIn = new DataInputStream(so.getInputStream());
        while(true){
                System.out.print("Da ");
                poz = socketIn.readUnsignedShort();
                System.out.print("Nope");
                if(poz==-1) break;
                System.out.print(poz+" ");
        }
//      System.out.println("");
}

private static void closeStreams(Socket socket, BufferedReader reader) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Could not close socket!");
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Could not close reader!");
            }
        }
    }

}
