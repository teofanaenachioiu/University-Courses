package service;

import domain.Tema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Consola {
    private Service s=new Service();
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    public void meniu(){
        System.out.println("Meniu");
        System.out.println("1. Adauga tema");
        System.out.println("2. Prelungire deadline");
        System.out.println("0. Exit");
    }

    public void callAdaugaTema() {
        try {
            System.out.print("ID: ");
            String id = br.readLine();
            System.out.print("Descriere: ");
            String descriere = br.readLine();
            System.out.print("Deadline: ");
            String deadline = br.readLine();
            System.out.print("Predare: ");
            String predare = br.readLine();
            if (s.adaugaTema(new Tema(Integer.parseInt(id), descriere, Integer.parseInt(deadline), Integer.parseInt(predare))) == null)
                System.out.println("Tema adaugata");
            else System.out.println("Tema existenta");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            System.out.println("Dati numere pentru id,deadline si data predarii");
        }


    }
    private void callPrelungireDeadline(){
        try {
            System.out.print("ID: ");
            String id=br.readLine();
            System.out.print("Deadline nou: ");
            String deadline=br.readLine();
            if(s.prelungireDeadLine(Integer.parseInt(id),Integer.parseInt(deadline))==null) System.out.println("Problemee");
            else System.out.println("Deadline actualizat");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            meniu();
            System.out.print("Comanda: ");
            String cmd;
            try {
                cmd=br.readLine();
            } catch (IOException e) {
                System.out.println("Comanda invalida!");
                continue;
            }
            if (cmd.equals("0")) {
                System.out.println("Bye!");
                break;
            }
            if (cmd.equals("1")){
                callAdaugaTema();
                continue;
            }
            if(cmd.equals("2")){
                callPrelungireDeadline();
                continue;
            }
            System.out.println("Comanda inv!");
        }

    }

}
