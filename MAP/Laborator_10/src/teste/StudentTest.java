package teste;

import domain.Student;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {
    private Student s=new Student("9999","Enachioiu Teofana","223","teoffa@yahoo.com","Adriana Guran");

    @Test
    public void getNume() {
        assertEquals(s.getNume(),"Enachioiu Teofana");
    }

    @Test
    public void setNume() {
        s.setNume("Teona");
        assertEquals(s.getNume(),"Teona");
    }

    @Test
    public void getGrupa() {
        assertEquals(s.getGrupa(),"223");
    }

    @Test
    public void setGrupa() {
        s.setGrupa("221");
        assertEquals(s.getGrupa(),"221");
    }

    @Test
    public void getEmail() {
        assertEquals(s.getEmail(),"teoffa@yahoo.com");
    }

    @Test
    public void setEmail() {
        s.setEmail("teo@yahoo.com");
        assertEquals(s.getEmail(),"teo@yahoo.com");
    }

    @Test
    public void getIndrumatorLab() {
        assertEquals(s.getIndrumatorLab(),"Adriana Guran");
    }

    @Test
    public void setIndrumatorLab() {
        s.setIndrumatorLab("Adriana");
        assertEquals(s.getIndrumatorLab(),"Adriana");
    }

    @Test
    public void getID() {
        assertEquals(s.getID(),"9999");
    }

    @Test
    public void setID() {
        s.setID("1000");
        assertEquals(s.getID(),"1000");
    }
}