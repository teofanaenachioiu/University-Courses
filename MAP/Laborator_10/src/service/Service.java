package service;

import domain.*;
import javafx.util.Pair;
import repository.*;
import utils.Observable;
import utils.Observer;
import utils.StudentChangeEvent;
import validator.ValidatorNota;
import validator.ValidatorStudent;
import validator.ValidatorTema;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Clasa Service
 */
public class Service implements Observable<StudentChangeEvent> {
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
        repoS=new StudentRepoXML(fileNameStd,new ValidatorStudent());
        repoT=new TemaRepoXML(fileNameTema,new ValidatorTema());
        repoN=new NotaRepoXML(fileNameNote, new ValidatorNota());
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
    public Optional<Student> adaugaStudent(String id, String nume, String grupa, String email, String profLab){
        return repoS.save(Optional.of(new Student(id,nume,grupa,email,profLab)));
    }

    /**
     * Stergere student
     * @param id id-ul studentului (String)
     * @return
     */
    public Optional<Student> stergeStudent(String id){
        return repoS.delete(Optional.ofNullable(id));
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
        Student s = repoS.findOne(Optional.ofNullable(id)).get();

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

        repoS.update(Optional.of(new Student(id,nume,grupa,email,prof)));
        return updated;
    }


    public TreeMap<String,String> studentList(){
        TreeMap<String, String> tree=new TreeMap();
        for(Student s:repoS.findAll())
            tree.put(s.getID(),s.getNume()+" (ID:"+s.getID()+")");
        return tree;
    }

    /**
     * Cauta un student in service
     * @param id String - id-ul studentului
     * @return entitate student / null
     */
    public Optional<Student> cautaStudent(String id){
        return repoS.findOne(Optional.of(id));
    }

    public void stergeStudenti(){
        Iterable<Student> lista=repoS.findAll();
        for(Student s:lista) repoS.delete(Optional.ofNullable(s.getID()));

    }

    public Optional<Tema> cautaTema(String id){
        return repoT.findOne(Optional.of(id));
    }
    /**
     * Lista de studenti
     * @return iterabil
     */
    public Iterable<Tema> listaTeme(){
        return repoT.findAll();
    }

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
    public Optional<Tema> adaugaTema(String id, String descriere, String deadline, String predare){
        return repoT.save(Optional.of(new Tema(id,descriere,deadline,predare)));
    }

    /**
     * Sterge o tema din service
     * @param id - String (id-ul temei)
     * @return entiatea stearsa(daca id-ul a fost gasit) / null (id-ul nu a fost gasit)
     */
    public Optional<Tema> stergeTema(String id){
        return repoT.delete(Optional.of(id));
    }

    /**
     * Prelungirea deadline pentru o tema
     * @param id - String (id-ul temei)
     * @param data -String (noul deadline)
     * @return entitatea actualizata
     */

    public Optional<Tema> prelungireDeadLine(String id, String data){
        Optional<Tema> t=repoT.findOne(Optional.of(id));

        t.ifPresent(tt->{
            Integer dataVeche = tt.getDeadline();
            tt.setDeadline(data);
            repoT.update(Optional.of(new Tema(id, tt.getDescriere(), tt.getDeadline().toString(), tt.getDataPredare().toString())));
        });

        if(t.get().getDeadline().equals(t.get().getDataPredare())) return Optional.empty();
        return t;
    }

    public Optional<Nota> stergeNota(String idS, String idT){
        return repoN.delete(Optional.of(new Pair(idS,idT)));

    }

    private String writeContent(Nota nota,String feedback){
        String msg;
        msg="Tema: "+nota.getTemaID()+"\n";
        msg=msg+"Nota: "+nota.getNotaProf()+"\n";
        Tema tema=repoT.findOne(Optional.of(nota.getTemaID())).get();
        msg=msg+"Predata in saptamana: "+tema.getDataPredare()+"\n";
        msg=msg+"Deadline: "+tema.getDeadline()+"\n";
        msg=msg+"Feedback: "+feedback+"\n";
        return msg;
    }

    private void adaugaInFile(Nota nota, String feedback){
        Student student=cautaStudent(nota.getStudentID()).get();

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

    public Optional<Nota> adaugaNota(String idS, String idT, String data, String notaProf,String feedback, boolean motivat){
        Nota entity=new Nota(idS,idT,data,notaProf);
        if(!repoT.findOne(Optional.of(idT)).isPresent())
            return Optional.of(entity);
        if(!repoS.findOne(Optional.of(idS)).isPresent())
            return Optional.of(entity);
        if(repoN.findOne(Optional.of(new Pair(idS,idT))).isPresent())
            return Optional.of(entity);
        if(!motivat) {
            Float nota=calculeazaNota(data,notaProf,repoT.findOne(Optional.of(idT)).get());
            entity.setNotaProf(nota.toString());
        }
        adaugaInFile(entity,feedback);
        return repoN.save(Optional.of(entity));
    }

    public Float calculeazaNota(String data,String notaProf, Tema tema) {
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

    private <T> Iterable <T> filter(Iterable <T> list, Predicate<T> cond)
    {
        List<T> rez=new ArrayList<>();
        list.forEach((T x)->{if (cond.test(x)) rez.add(x);});
        return rez;
    }

    public Iterable<Student> byTeacher(String name) {
        return filter(repoS.findAll(), student -> student.getIndrumatorLab().contains(name));
    }

    public Iterable<Student> byGroupe(String name) {
        return filter(repoS.findAll(), student -> student.getGrupa().contains(name));
    }

    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentChangeEvent t) {
        observers.forEach(x->x.update(t));
    }

    public Student getStudent(String detalii) {
        for (Student s:listaStudenti()) {
            if (detalii.contains(s.getID()))
                return s;
        }
        return null;
    }

    public List<NotaDTO> listaNoteDTO(){
        List<NotaDTO> lista=new ArrayList<>();
        for(Nota nota:repoN.findAll()){
            Student s=repoS.findOne(Optional.ofNullable(nota.getStudentID())).get();
            NotaDTO notaDTO=new NotaDTO(nota);
            notaDTO.setNumeStudent(s.getNume());
            lista.add(notaDTO);
        }
        return lista;
    }

    public String getIdStudent(String string){
        String[] parts=string.split("[:)]");
        return parts[1];
    }

    private Integer getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    /**
     * Se determina numarul laboratorului (tinand cont de vacanta de iarna)
     * @return dif (nurmarul laboratorului)
     */
    public Integer getLabNumber(){
        Integer dif=getCurrentWeek()-39;
        if(dif<1||dif>16) return null;
        if(getCurrentWeek().equals(13) || getCurrentWeek().equals(14))
            return 12;
        if(getCurrentWeek().equals(15)) return dif-1;
        if(getCurrentWeek().equals(16)) return dif-2;
        return dif;
    }

    public Integer getCurrentAssignment(){
        int currentLab=getLabNumber();
        List<Tema> lista= StreamSupport.stream(repoT.findAll().spliterator(),false).
                filter(t->t.getDataPredare()<=currentLab&&currentLab<=t.getDeadline()).collect(Collectors.toList());
        return Integer.parseInt(lista.get(0).getID());
    }

    public Nota cautaNota(String idStudent, String idTema){
        List<Nota> lista=StreamSupport.stream(repoN.findAll().spliterator(),false).
                filter(n->n.getStudentID().equals(idStudent)&&n.getTemaID().equals(idTema)).collect(Collectors.toList());
        return lista.get(0);
    }

    public Integer getWeek(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    public Integer getWeekUni(Integer saptCurenta){
        Integer dif=saptCurenta-39;
        if(dif<1||dif>16) return null;
        if(saptCurenta.equals(13) || saptCurenta.equals(14))
            return 12;
        if(saptCurenta.equals(15)) return dif-1;
        if(saptCurenta.equals(16)) return dif-2;
        return dif;
    }

    private List<Float> initNote(){
        List<Float> note;
        note = Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f);
        Integer crr=getCurrentAssignment();
        for(int i=0;i<crr-1;i++) note.set(i,1f);
        return note;
    }

    public Collection<NotaDTO2> noteLaboratoare(){

        HashMap<String,NotaDTO2> mapNote=new HashMap();
        for(Nota n:repoN.findAll()){
            NotaDTO2 notaDTO2=new NotaDTO2(n.getStudentID(),repoS.findOne(Optional.of(n.getStudentID())).get().getNume());
            notaDTO2.setNote(initNote());
            mapNote.putIfAbsent(n.getStudentID(),notaDTO2);
           // NotaDTO2 newNota=mapNote.get(n.getStudentID()).setNotaLab(n.getTemaID(),n.getNotaProf());
            //mapNote.replace(n.getStudentID(),newNota);
            mapNote.computeIfPresent(n.getStudentID(),(x,y)->y.setNotaLab(n.getTemaID(),n.getNotaProf()));
        }
        return mapNote.values();
    }

    private Float calculeazaMedia(List<Float> listaNote){
        Float suma=0f;
        Integer sumaPonderi=0;
        Integer lab=1;
        for(Float nota:listaNote){
            Tema t=repoT.findOne(Optional.of(lab.toString())).get();
            Integer pondere=t.getDeadline()-t.getDataPredare();
            suma=suma+pondere*nota;
            sumaPonderi+=pondere;
            lab+=1;
        }
        return suma/sumaPonderi;
    }

    public Collection<Media> medieStudenti(){
        Collection<Media> listaMedii=new ArrayList<>();
        Float medie;
        for (NotaDTO2 nota:noteLaboratoare()) {
            medie=calculeazaMedia(nota.getNote());
            String grupa=repoS.findOne(Optional.of(nota.getIdStudent())).get().getGrupa();
            Media m=new Media(nota.getIdStudent(),nota.getNumeStudent(),grupa,medie);
            listaMedii.add(m);
        }
        return listaMedii;
    }

}
