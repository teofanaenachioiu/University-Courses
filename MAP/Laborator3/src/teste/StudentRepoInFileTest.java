package teste;

import domain.Student;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.StudentRepoInFile;
import validator.Validator;
import validator.ValidatorStudent;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepoInFileTest {
    private CrudRepository<String, Student> repo=new StudentRepoInFile("./src/teste/Studenti.txt", new ValidatorStudent());
    private Student s1=new Student("9999","Claudia","223","clau@yahoo.com","A Guran");
    private Student s2=new Student("1001","Elena","223","ela@yahoo.com","A Guran");

    @Test
    void save() {
        try{
            repo.save(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.delete("9999");
        assertNull(repo.save(s1));
        repo.delete("9999");
        assertEquals(s2,repo.save(s2));

    }

    @Test
    void findOne() {
        try{
            repo.findOne(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertNull(repo.findOne("1010"));
        repo.save(s1);
        assertEquals(s1,repo.findOne("9999"));
    }

    @Test
    void findAll() {
        int size=0;
        for(Student s:repo.findAll()) size++;
        assertEquals(5,size);
    }

    @Test
    void delete() {
        try{
            repo.delete(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.save(s1);
        assertNull(repo.delete("5"));
        assertEquals(s1,repo.delete("9999"));
    }

    @Test
    void update() {
        try{
            repo.update(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.save(s1);
        assertNull(repo.update(s1));
        Student s3=new Student("1011","Elena","223","ela@yahoo.com","A Guran");

        assertEquals(s3,repo.update(s3));
    }
}