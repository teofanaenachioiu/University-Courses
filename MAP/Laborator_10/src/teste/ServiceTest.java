package teste;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Test;
import service.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service serv=new Service("./src/teste/Studenti.txt","./src/teste/Teme.txt","./src/teste/Catalog.txt");

    @Test
    void adaugaStudent() {
        assertEquals(Optional.empty(),serv.adaugaStudent("9999","Claudia","223","clau@yahoo.com","Guran A"));
        assertEquals(Optional.of(new Student("1001","Claudia","223","clau@yahoo.com","Guran A")),serv.adaugaStudent("1001","Claudia","223","clau@yahoo.com","Guran A"));
        serv.stergeStudent("9999");
    }

    @Test
    void stergeStudent() {
        assertEquals(Optional.empty(),serv.stergeStudent("1010"));
        serv.adaugaStudent("9999","Claudia","223","clau@yahoo.com","Guran A");
        Student s=new Student("9999","Claudia","223","clau@yahoo.com","Guran A");
        assertEquals(Optional.of(s),serv.stergeStudent("9999"));
    }

    @Test
    void cautaStudent() {
        assertEquals(Optional.empty(),serv.cautaStudent("1010"));
        Student s=new Student("1001","Claudia","223","clau@yahoo.com","Guran A");
        assertEquals(Optional.of(s),serv.cautaStudent("1001"));
    }

    @Test
    void listaStudenti() {
        int size=0;
        for(Student s:serv.listaStudenti()) size++;
        assertEquals(5,size);
    }

    @Test
    void actualizareStudent() {
        assertFalse(serv.actualizareStudent("1001","","","",""));
        serv.adaugaStudent("9999","Claudia","223","clau@yahoo.com","Guran A");
        Student s=new Student("9999","Claudiu","223","clau@yahoo.com","Guran A");
        assertTrue(serv.actualizareStudent("9999","Claudiu","","",""));
        assertEquals(Optional.of(s),serv.cautaStudent("9999"));
        serv.stergeStudent("9999");
    }

    @Test
    void adaugaTema() {
        assertEquals(Optional.empty(),serv.adaugaTema("7","Lab 7","13","7"));
        serv.stergeTema("7");
        Tema t=new Tema ("1","Lab 1", "2","1");
        assertEquals(Optional.of(t),serv.adaugaTema("1","Lab 1", "2","1"));
    }

    @Test
    void prelungireDeadLine() {
        assertEquals(Optional.empty(),serv.prelungireDeadLine("1", "2"));
        assertEquals(Optional.empty(),serv.prelungireDeadLine("1", "1"));
        assertEquals(Optional.of(new Tema("4","Catalog MAP - iteratia 7","14","12")),serv.prelungireDeadLine("4","14"));
        serv.prelungireDeadLine("4","13");
    }

    @Test
    void adaugaNota() {
        assertEquals(Optional.empty(),serv.adaugaNota("1005","4","10","10","Esti bun",false));
        serv.stergeNota("1005","4");
        Nota nota=new Nota("1001","4","10","9");
        assertEquals(Optional.of(nota),serv.adaugaNota("1001","4","10","9","Merge",false));
    }
}