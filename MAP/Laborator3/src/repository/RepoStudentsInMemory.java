package repository;

import domain.Student;
import exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class RepoStudentsInMemory implements CrudRepository<Integer, Student> {
    private List<Student> repo=new ArrayList<Student>();

    @Override
    public Student findOne(Integer integer) {
        if(integer==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        for (Student s:repo) {
            if(s.getID().equals(integer)){
                return s;
            }
        }
        return null;
    }

    @Override
    public Iterable<Student> findAll() {
        return repo;
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        if(entity==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        if(findOne(entity.getID())!=null) return entity;
        else {
            repo.add(entity);
            return null;
        }
    }

    @Override
    public Student delete(Integer integer) {
        if(integer==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        else{
            Student s=findOne(integer);
            if(s==null) return null;
            else{
                repo.remove(s);
                return s;
            }
        }
    }

    @Override
    public Student update(Student entity) {
        if(entity==null) {
            throw new IllegalArgumentException("Nu ai dat parametru");
        }
        Student s=findOne(entity.getID());
        if(s==null) return entity;
        else {
            repo.remove(s);
            repo.add(entity);
            return null;
        }
    }
}
