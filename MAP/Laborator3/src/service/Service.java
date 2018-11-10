package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.util.Pair;
import repository.*;
import validator.ValidatorNota;
import validator.ValidatorStudent;
import validator.ValidatorTema;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clasa Service
 */
public class Service {
    private String fileNameStd;
    private String fileNameTema;
    private String fileNameNote;
    private CrudRepository<String, Student> repoS;
    private CrudRepository<String, Tema> repoT;
    private CrudRepository<Pair<String,String>, Nota> repoN;

    /**
     * Constructor Service
     * @param fileNameStd fisierul cu date despre studenti
     * @param fileNameTema fisireul cu date despre teme
     */
    public Service(String fileNameStd, String fileNameTema, String fileNameNote) {
        this.fileNameStd = fileNameStd;
        this.fileNameTema = fileNameTema;
        this.fileNameNote=fileNameNote;
        repoS=new StudentRepoInFile(fileNameStd,new ValidatorStudent());
        repoT=new TemaRepoInFile(fileNameTema,new ValidatorTema());
        repoN=new NotaRepoInFile(fileNameNote, new ValidatorNota());
    }

    /**
     * Adauga student
     * @param id id-ul studentului (String)
     * @param nume numele studentului (String)
     * @param grupa grupa studentului (String)
     * @param email emailul studentului (String)
     * @param profLab profesorul coordonator de la laborator (String)
     * @return entitate student
     */
    public Student adaugaStudent(String id, String nume, String grupa, String email, String profLab){
        return repoS.save(new Student(id,nume,grupa,email,profLab));
    }

    /**
     * Stergere student
     * @param id id-ul studentului (String)
     * @return
     */
    public Student stergeStudent(String id){
        return repoS.delete(id);
    }

    /**
     * Actualizeaza datele despre un student
     * @param id id-ul studentului (String)
     * @param nume numele studentului (String)
     * @param grupa grupa studentului (String)
     * @param email emailul studentului (String)
     * @param prof profesorul coordonator de la laborator (String)
     * @return entitate student actualizata
     */
    public boolean actualizareStudent(String id, String nume, String grupa, String email, String prof){
        boolean updated=false;
        Student s= repoS.findOne(id);
        if(nume.equals(""))
            nume=s.getNume();
        else updated=true;

        if(grupa.equals(""))
            grupa=s.getGrupa();
        else updated=true;

        if(email.equals(""))
            email=s.getEmail();
        else updated=true;

        if(prof.equals(""))
            prof=s.getIndrumatorLab();
        else updated=true;

        repoS.update(new Student(id,nume,grupa,email,prof));
        return updated;
    }

    /**
     * Cauta un student in service
     * @param id String - id-ul studentului
     * @return entitate student / null
     */
    public Student cautaStudent(String id){
        return repoS.findOne(id);
    }

    /**
     * Lista de studenti
     * @return iterabil
     */
    public Iterable<Student> listaStudenti(){
        return repoS.findAll();
    }

    /**
     *
     * @param id - String (id-ul temei)
     * @param descriere - String (descrierea temei)
     * @param deadline - String (saptamana in care trebuie predata tema)
     * @param predare - String (saptamana in care a fost data tema)
     * @return entitatea Tema nou creata(id-ul exista deva in service) / null (entitatea a fost adaugata)
     */
    public Tema adaugaTema(String id, String descriere, String deadline, String predare){
        return repoT.save(new Tema(id,descriere,deadline,predare));
    }

    /**
     * Sterge o tema din service
     * @param id - String (id-ul temei)
     * @return entiatea stearsa(daca id-ul a fost gasit) / null (id-ul nu a fost gasit)
     */
    public Tema stergeTema(String id){
        return repoT.delete(id);
    }

    /**
     * Prelungirea deadline pentru o tema
     * @param id - String (id-ul temei)
     * @param data -String (noul deadline)
     * @return entitatea actualizata
     */
    public Tema prelungireDeadLine(String id, String data){
        Tema t=repoT.findOne(id);
        if(t!=null) {
            Integer dataVeche=t.getDeadline();
            t.setDeadline(data);
            repoT.update(new Tema(id,t.getDescriere(),t.getDeadline().toString(),t.getDataPredare().toString()));
            if(dataVeche.equals(t.getDeadline())) return null;
        }
        return t;
    }

    public Nota stergeNota(String idS, String idT){
        return repoN.delete(new Pair(idS,idT));

    }

    private String writeContent(Nota nota,String feedback){
        String msg;
        msg="Tema: "+nota.getTemaID()+"\n";
        msg=msg+"Nota: "+nota.getNotaProf()+"\n";
        Tema tema=repoT.findOne(nota.getTemaID());
        msg=msg+"Predata in saptamana: "+tema.getDataPredare()+"\n";
        msg=msg+"Deadline: "+tema.getDeadline()+"\n";
        msg=msg+"Feedback: "+feedback;
        return msg;
    }

    private void adaugaInFile(Nota nota, String feedback){
        Student student=cautaStudent(nota.getStudentID());

        String fileName="./src/data/"+student.getNume()+".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true))) {
            bw.write(writeContent(nota,feedback));
            bw.newLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Nota adaugaNota(String idS, String idT, String data, String notaProf,String feedback, boolean motivat){
        Nota entity=new Nota(idS,idT,data,notaProf);
        if(repoT.findOne(idT)==null)return entity;
        if(repoS.findOne(idS)==null)return entity;
        if(!motivat) {
            Float nota=calculeazaNota(data,notaProf,repoT.findOne(idT));
            entity.setNotaProf(nota.toString());
        }
        adaugaInFile(entity,feedback);
        return repoN.save(entity);
    }

    private Float calculeazaNota(String data,String notaProf, Tema tema) {
        Float nota=Float.parseFloat(notaProf);
        Integer dif = Integer.parseInt(data) - tema.getDeadline();
        if (dif > 0 && dif <= 2) {
            return  nota - dif * 2.5f;
        } else if (dif<=0){
            return (float) nota;
        }
        else{
            return 1f;
        }
    }
}
