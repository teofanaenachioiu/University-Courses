package consola;

import domain.Student;
import domain.Tema;
import repository.ValidationException;
import service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class Consola {
    private Service serv=new Service("./src/data/Studenti.xml","./src/data/Teme.xml","./src/data/Catalog.xml");
    private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    private void meniu(){
        System.out.println("Meniu");
        System.out.println("1. Adauga student");
        System.out.println("2. Sterge student");
        System.out.println("3. Actualizare date student");
        System.out.println("4. Cauta student");
        System.out.println("5. Vizualizare lista studenti");
        System.out.println("6. Adauga tema");
        System.out.println("7. Prelungire deadline");
        System.out.println("8. Asignare nota");
        System.out.println("0. Exit");
    }

    private void callAdaugaStudent(){
        try {
            System.out.print("ID: ");
            String id=br.readLine();
            System.out.print("Nume: ");
            String nume=br.readLine();
            System.out.print("Grupa: ");
            String grupa=br.readLine();
            System.out.print("Email: ");
            String email=br.readLine();
            System.out.print("Profesor indrumator: ");
            String prof=br.readLine();
            Optional<Student> optStudent=serv.adaugaStudent(id,nume,grupa,email,prof);

            if(optStudent.isPresent())
                System.out.println("Studentul nu a putut fi adaugat");
            else
                System.out.println("Studentul a fost adaugat");

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void callStergeStudent(){
        try {
            System.out.print("ID student: ");
            String id=br.readLine();
            if(!serv.stergeStudent(id).isPresent())
                System.out.println("Nu exista niciun student cu acest id");
            else System.out.println("Studentul a fost sters");
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void callActualizareStudent(){
        try {
            System.out.print("ID student: ");
            String id=br.readLine();
            if(!serv.cautaStudent(id).isPresent()) System.out.println("Nu exista niciun student cu acest id");
            else{
                boolean updated=false;

                System.out.print("Nume: ");
                String nume=br.readLine();

                System.out.print("Grupa: ");
                String grupa=br.readLine();

                System.out.print("Email: ");
                String email=br.readLine();

                System.out.print("Profesor indrumator: ");
                String prof=br.readLine();

                if(!serv.actualizareStudent(id,nume,grupa,email,prof)) System.out.println("Nu s-au introdus date noi");
                else System.out.println("Datele studentului au fost modificate");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void callCautaStudent(){
        try {
            System.out.print("ID student: ");
            String id=br.readLine();
            if(!serv.cautaStudent(id).isPresent()) System.out.println("Nu exista niciun student cu acest numar matricol");
            else System.out.println("Studentul cautat este "+serv.cautaStudent(id).get());

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void callListaStudenti(){
        serv.listaStudenti().forEach(System.out::println);
    }

    private void callAdaugaTema() {
        try {
            System.out.print("ID: ");
            String id = br.readLine();
            System.out.print("Descriere: ");
            String descriere = br.readLine();
            System.out.print("Deadline: ");
            String deadline = br.readLine();
            System.out.print("Predare: ");
            String predare = br.readLine();
            if (!serv.adaugaTema(id, descriere, deadline, predare).isPresent())
                System.out.println("Tema adaugata");
            else System.out.println("Tema existenta");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            System.out.println("Dati numere pentru id,deadline si data predarii");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

    }
    private void callPrelungireDeadline(){
        try {
            System.out.print("ID: ");
            String id=br.readLine();
            System.out.print("Deadline nou: ");
            String deadline=br.readLine();
            if(!serv.prelungireDeadLine(id,deadline).isPresent()) System.out.println("Problemee");
            else System.out.println("Deadline actualizat");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void callAsignareNota() {
        try {
            System.out.print("ID student: ");
            String idS=br.readLine();
            System.out.print("ID tema: ");
            String idT=br.readLine();
            System.out.print("Nota: ");
            String nota=br.readLine();
            System.out.print("Data: ");
            String data=br.readLine();
            System.out.print("Feedback: ");
            String feedback=br.readLine();

            boolean motivat=false;
            System.out.println("Studentul a lipsit motivat? (y/n)");
            String ans=br.readLine();
            if(ans.equals("y")||ans.equals("Y")) motivat=true;
            if(serv.adaugaNota(idS,idT,data,nota,feedback,motivat).isPresent()) System.out.println("Nota nu a putut fi adaugata!");
            else {
                if(motivat){
                    System.out.println("Nota maxima "+nota);
                    System.out.println("Studentul a primit nota "+ nota);
                }
                else {
                    Tema t = serv.cautaTema(idT).get();
                    System.out.println("Nota maxima " + serv.calculeazaNota(data, "10", t));
                    System.out.println("Studentul a primit nota " + serv.calculeazaNota(data, nota, t));
                }
            }
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
            if (cmd.equals("1")) {
                callAdaugaStudent();
                continue;
            }
            if (cmd.equals("2")) {
                callStergeStudent();
                continue;
            }
            if (cmd.equals("3")) {
                callActualizareStudent();
                continue;
            }
            if (cmd.equals("4")) {
                callCautaStudent();
                continue;
            }
            if (cmd.equals("5")) {
                callListaStudenti();
                continue;
            }
            if (cmd.equals("6")){
                callAdaugaTema();
                continue;
            }
            if(cmd.equals("7")){
                callPrelungireDeadline();
                continue;
            }
            if(cmd.equals("8")){
                callAsignareNota();
                continue;
            }
            System.out.println("Comanda invalida!");
        }

    }



}
