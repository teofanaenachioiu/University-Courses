package teste;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Test;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service serv=new Service("./src/teste/Studenti.txt","./src/teste/Teme.txt");

    @Test
    void adaugaStudent() {
        assertNull(serv.adaugaStudent("9999","Claudia","223","clau@yahoo.com","Guran A"));
        assertEquals(new Student("1001","Claudia","223","clau@yahoo.com","Guran A"),serv.adaugaStudent("1001","Claudia","223","clau@yahoo.com","Guran A"));
        serv.stergeStudent("9999");
    }

    @Test
    void stergeStudent() {
        assertNull(serv.stergeStudent("1010"));
        serv.adaugaStudent("9999","Claudia","223","clau@yahoo.com","Guran A");
        Student s=new Student("9999","Claudia","223","clau@yahoo.com","Guran A");
        assertEquals(s,serv.stergeStudent("9999"));
    }

    @Test
    void cautaStudent() {
        assertNull(serv.cautaStudent("1010"));
        Student s=new Student("1001","Teofana","223","teo@yahoo.com","Guran A");
        assertEquals(s,serv.cautaStudent("1001"));
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
        assertEquals(s,serv.cautaStudent("9999"));
        serv.stergeStudent("9999");
    }

    @Test
    void adaugaTema() {
        assertNull(serv.adaugaTema("7","Lab 7","13","7"));
        serv.stergeTema("7");
        Tema t=new Tema ("1","Lab 1", "2","1");
        assertEquals(t,serv.adaugaTema("1","Lab 1", "2","1"));
    }

    @Test
    void prelungireDeadLine() {
        assertNull(serv.prelungireDeadLine("1", "2"));
        assertNull(serv.prelungireDeadLine("1", "1"));
        assertEquals(new Tema("4","Catalog MAP - iteratia 7","14","12"),serv.prelungireDeadLine("4","14"));
        serv.prelungireDeadLine("4","13");
    }
}