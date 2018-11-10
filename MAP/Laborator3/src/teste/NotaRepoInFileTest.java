package teste;

import domain.Nota;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.NotaRepoInFile;
import validator.ValidatorNota;

import static org.junit.jupiter.api.Assertions.*;

class NotaRepoInFileTest {
    private CrudRepository<Pair<String, String>, Nota> repo=new NotaRepoInFile("./src/teste/Catalog.txt", new ValidatorNota());

    @Test
    void findAll() {
        int size=0;
        for(Nota n:repo.findAll()) size++;
        assertEquals(2,size);
    }

    @Test
    void findOne() {
        try{
            repo.findOne(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertNull(repo.findOne(new Pair("1111","4")));
        Nota nota=new Nota("1001","4","10","10");
        repo.save(nota);
        assertEquals(nota,repo.findOne(new Pair("1001","4")));
    }

    @Test
    void save() {
        try{
            repo.save(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        Nota nota=new Nota("1001","2","10","10");
        assertNull(repo.save(nota));
        repo.delete(new Pair("1001","2"));
        Nota nota1=new Nota("1001","4","10","10");
        assertEquals(nota1,repo.save(nota1));

    }

    @Test
    void delete() {
        try{
            repo.delete(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        assertNull(repo.delete(new Pair("1001","2")));

        Nota nota=new Nota("1001","2","10","10");
        repo.save(nota);

        assertEquals(nota,repo.delete(new Pair("1001","2")));
    }

    @Test
    void update() {
        try{
            repo.update(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Nu ai dat parametru", e.getMessage());
        }
        Nota nota=new Nota("1001","4","10","9");
        Nota nota1=new Nota("1001","4","10","10");
        Nota nota2=new Nota("1010","4","10","10");
        assertNull(repo.update(nota));
        assertNull(repo.update(nota1));
        assertEquals(nota2,repo.update(nota2));
    }
}