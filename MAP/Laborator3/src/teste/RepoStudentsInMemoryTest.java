package teste;

import domain.Student;
import repository.ValidationException;
import org.junit.jupiter.api.Test;
import repository.RepoStudentsInMemory;
import validator.ValidatorStudent;

import static org.junit.jupiter.api.Assertions.*;

public class RepoStudentsInMemoryTest {
    RepoStudentsInMemory repo=new RepoStudentsInMemory();

    @Test
    void findOne() throws ValidationException {
        Student s1=new Student(2278,"Ioana",222,"ioana@yahoo.com","A Guran");
        repo.save(s1);
        assertEquals(repo.findOne(2278),s1);
        assertNull(repo.findOne(2277));
        try{
            repo.findOne(null);
        }
        catch (IllegalArgumentException e){
            assertEquals(e.getMessage(),"Nu ai dat parametru"); }
    }

    @Test
    void findAll() {
        Iterable<Student> studenti=repo.findAll();
    }

    @Test
    void save() {
        Student s1=new Student(2278,"Ioana",222,"ioana@yahoo.com","A Guran");
        boolean throwww=false;

        try{
            repo.save(null);
        }
        catch (IllegalArgumentException e){
            assertEquals(e.getMessage(),"Nu ai dat parametru");
            throwww=true;
        } catch (ValidationException e) {
            assertEquals("Nu ai dat parametru",e.getMessage());
        }
        assertTrue(throwww);

        throwww=false;
        try {
            assertNull(repo.save(s1));
        } catch (ValidationException e) {
            throwww=true;
        }
        assertFalse(throwww);

        throwww=false;
        try {
            assertEquals(repo.save(s1),s1);
        } catch (ValidationException e) {
            throwww=true;
        }
        assertFalse(throwww);

        Student s2=new Student(2279,"An4",222,"ana@yahoo.com","A Guran");
        throwww=false;
        try {
            assertEquals(repo.save(s2),s2);
        } catch (ValidationException e) {
            assertEquals("Nume incorect!",e.getMessage());
            throwww=true;
        }
        assertTrue(throwww);
    }

    @Test
    void delete() throws ValidationException {
        Student s1=new Student(2278,"Ioana",222,"ioana@yahoo.com","A Guran");
        boolean throwww=false;
        repo.save(s1);

        try {
            repo.delete(null);
        }
        catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Nu ai dat parametru");
            throwww = true; }
        assertTrue(throwww);

        assertNull(repo.delete(100));

        assertEquals(repo.delete(2278),s1);

    }

    @Test
    void update() throws ValidationException {
        Student s1=new Student(2278,"Ioana",222,"ioana@yahoo.com","A Guran");
        boolean throwww=false;
        repo.save(s1);

        try {
            repo.update(null);
        }
        catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Nu ai dat parametru");
            throwww = true; }

        Student s2=new Student(2279,"Ana",222,"ana@yahoo.com","A Guran");
        assertEquals(repo.update(s2),s2);

        Student s3=new Student(2278,"Oana",222,"ioana@yahoo.com","A Guran");
        assertNull(repo.update(s3));
    }
}