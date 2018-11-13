package teste;

import domain.Tema;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.RepoInMemory;
import validator.ValidatorTema;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepoInMemoryTest {
    private CrudRepository<String, Tema> repo=new RepoInMemory<String, Tema>(new ValidatorTema());
    private Tema t1=new Tema("1","Lab 1","2","1");
    private Tema t2=new Tema ("2","Lab 2","3","2");
    private Tema t3=new Tema ("3","Lab 3","4","3");

    @Test
    void save() {
        try{
            repo.save(Optional.empty());
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertEquals(Optional.empty(),repo.save(Optional.ofNullable(t1)));
        assertEquals(Optional.empty(),repo.save(Optional.ofNullable(t2)));
        assertEquals(Optional.empty(),repo.save(Optional.ofNullable(t3)));
        assertEquals(Optional.of(t1),repo.save(Optional.ofNullable(t1)));
    }

    @Test
    void findOne() {
        try{
            repo.findOne(Optional.empty());
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertEquals(Optional.empty(),repo.findOne(Optional.of("4")));
        repo.save(Optional.ofNullable(t1));
        repo.save(Optional.ofNullable(t2));
        repo.save(Optional.ofNullable(t3));
        assertEquals(Optional.of(t1),repo.findOne(Optional.of("1")));
    }

    @Test
    void findAll() {
        int size=0;
        repo.save(Optional.ofNullable(t1));
        repo.save(Optional.ofNullable(t2));
        repo.save(Optional.ofNullable(t3));
        for(Tema t:repo.findAll()) size++;
        assertEquals(3,size);
    }

    @Test
    void delete() {
        try{
            repo.delete(Optional.empty());
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.save(Optional.ofNullable(t1));
        repo.save(Optional.ofNullable(t2));
        repo.save(Optional.ofNullable(t3));
        assertEquals(Optional.empty(),repo.delete(Optional.of("5")));
        assertEquals(Optional.of(t1),repo.delete(Optional.of("1")));
    }

    @Test
    void update() {
        try{
            repo.update(Optional.empty());
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        repo.save(Optional.ofNullable(t1));
        repo.save(Optional.ofNullable(t2));
        repo.save(Optional.ofNullable(t3));
        Tema t33=new Tema ("3","Lab 10","10","3");
        Tema t10=new Tema ("10","Lab 10","10","3");
        assertEquals(Optional.empty(),repo.update(Optional.of(t33)));
        assertEquals(Optional.of(t10),repo.update(Optional.of(t10)));
    }
}