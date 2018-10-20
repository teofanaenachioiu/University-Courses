package teste;

import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaTest {
    Tema t=new Tema(103,"Laborator 3",5,5, (float) 10);

    @Test
    void getDescriere() {
        assertEquals("Laborator 3",t.getDescriere());
    }

    @Test
    void setDescriere() {
        t.setDescriere("Laborator 3-4");
        assertEquals("Laborator 3-4",t.getDescriere());
    }

    @Test
    void getDeadline() {
        assertEquals(5,(int)t.getDeadline());
    }

    @Test
    void setDeadline() {
        t.setDeadline(4);
        assertEquals(4,(int)t.getDeadline());
    }

    @Test
    void getDataPredare() {
        assertEquals(5,(int)t.getDataPredare());
    }

    @Test
    void setDataPredare() {
        t.setDataPredare(6);
        assertEquals(6,(int)t.getDataPredare());
    }

    @Test
    void getNota() {
        assertEquals( 10,(float)t.getNota());

        t.setDataPredare(6);
        assertEquals( 7.5,(float)t.getNota());

        t.setDataPredare(7);
        assertEquals( 5,(float)t.getNota());

        t.setDataPredare(8);
        assertEquals( 1,(float)t.getNota());

        t.setDeadline(8);
        assertEquals( 10,(float)t.getNota());

        t.setDataPredare(7);
        t.setDeadline(6);
        assertEquals( 7.5,(float)t.getNota());
    }

    @Test
    void setNota() {
        t.setNota((float)7);
        assertEquals(7,(float)t.getNota());
    }

    @Test
    void getID() {
        assertEquals((float)103,(float)t.getID());
    }

    @Test
    void setID() {
        t.setID(100);
        assertEquals((float)100,(float)t.getID());
    }
}