package service;

import domain.Student;
import domain.Tema;
import repository.CrudRepository;
import repository.StudentRepoInFile;
import repository.TemaRepoInFile;
import validator.ValidatorStudent;
import validator.ValidatorTema;

/**
 * Clasa Service
 */
public class Service {
    private String fileNameStd;
    private String fileNameTema;
    private CrudRepository<String, Student> repoS;
    private CrudRepository<String, Tema> repoT;

    /**
     * Constructor Service
     * @param fileNameStd fisierul cu date despre studenti
     * @param fileNameTema fisireul cu date despre teme
     */
    public Service(String fileNameStd, String fileNameTema) {
        this.fileNameStd = fileNameStd;
        this.fileNameTema = fileNameTema;
        repoS=new StudentRepoInFile(fileNameStd,new ValidatorStudent());
        repoT=new TemaRepoInFile(fileNameTema,new ValidatorTema());
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

}
