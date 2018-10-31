package service;

import domain.Student;
import domain.Tema;
import repository.CrudRepository;
import repository.StudentRepoInFile;
import repository.TemaRepoInFile;
import validator.ValidatorStudent;
import validator.ValidatorTema;


public class Service {
    private String fileNameStd;
    private String fileNameTema;
    private CrudRepository<String, Student> repoS;
    private CrudRepository<String, Tema> repoT;

    public Service(String fileNameStd, String fileNameTema) {
        this.fileNameStd = fileNameStd;
        this.fileNameTema = fileNameTema;
        repoS=new StudentRepoInFile(fileNameStd,new ValidatorStudent());
        repoT=new TemaRepoInFile(fileNameTema,new ValidatorTema());
    }


    public Student adaugaStudent(String id, String nume, String grupa, String email, String profLab){
        return repoS.save(new Student(id,nume,grupa,email,profLab));
    }

    public Student stergeStudent(String id){
        return repoS.delete(id);
    }

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

    public Student cautaStudent(String id){
        return repoS.findOne(id);
    }

    public Iterable<Student> listaStudenti(){
        return repoS.findAll();
    }

    public Tema adaugaTema(String id, String descriere, String deadline, String predare){
        return repoT.save(new Tema(id,descriere,deadline,predare));
    }

    public Tema stergeTema(String id){
        return repoT.delete(id);
    }

    public Tema prelungireDeadLine(String id, String data){
        Tema t=repoT.findOne(id);
        if(t!=null) {
            Integer dataVeche=t.getDeadline();
            t.setDeadline(data);
            if(dataVeche.equals(t.getDeadline())) return null;
        }
        return t;
    }

}
