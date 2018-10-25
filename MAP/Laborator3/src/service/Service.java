package service;

import domain.Student;
import domain.Tema;
import repository.CrudRepository;
import repository.StudentRepoInFile;
import repository.TemaRepoInFile;
import validator.ValidatorStudent;
import validator.ValidatorTema;

public class Service {
    private String fileNameStd="./src/data/Studenti.txt";
    private String fileNameTema="./src/data/Teme.txt";
    private CrudRepository<Integer, Student> repoS=new StudentRepoInFile(fileNameStd,new ValidatorStudent());
    private CrudRepository<Integer, Tema> repoT=new TemaRepoInFile(fileNameTema,new ValidatorTema());

    public Tema adaugaTema(Tema entity){
        return repoT.save(entity);
    }

    public Tema prelungireDeadLine(Integer id, Integer data){
        Tema t=repoT.findOne(id);
        if(t!=null) {
            Integer dataVeche=t.getDeadline();
            t.setDeadline(data);
            if(dataVeche==t.getDeadline()) return null;
        }
        return t;
    }

}
