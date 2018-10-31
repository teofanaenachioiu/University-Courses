package teste;

import domain.Tema;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.RepoInMemory;
import validator.ValidatorTema;

import static org.junit.jupiter.api.Assertions.*;

class RepoInMemoryTest {
    private CrudRepository<String, Tema> repo=new RepoInMemory<String, Tema>(new ValidatorTema());
    private Tema t1=new Tema("1","Lab 1","2","1");
    private Tema t2=new Tema ("2","Lab 2","3","2");
    private Tema t3=new Tema ("3","Lab 3","4","3");

    @Test
    void save() {
        try{
            repo.save(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertNull(repo.save(t1));
        assertNull(repo.save(t2));
        assertNull(repo.save(t3));
        assertEquals(t1,repo.save(t1));
    }

    @Test
    void findOne() {
        try{
            repo.findOne(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertNull(repo.findOne("4"));
        repo.save(t1);
        repo.save(t2);
        repo.save(t3);
        assertEquals(t1,repo.findOne("1"));
    }

    @Test
    void findAll() {
        int size=0;
        repo.save(t1);
        repo.save(t2);
        repo.save(t3);
        for(Tema t:repo.findAll()) size++;
        assertEquals(3,size);
    }

    @Test
    void delete() {
        try{
            repo.delete(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.save(t1);
        repo.save(t2);
        repo.save(t3);
        assertNull(repo.delete("5"));
        assertEquals(t1,repo.delete("1"));
    }

    @Test
    void update() {
        try{
            repo.update(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.save(t1);
        repo.save(t2);
        repo.save(t3);
        Tema t33=new Tema ("3","Lab 10","10","3");
        Tema t10=new Tema ("10","Lab 10","10","3");
        assertNull(repo.update(t33));
        assertEquals(t10,repo.update(t10));
    }
}