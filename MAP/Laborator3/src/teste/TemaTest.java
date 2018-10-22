package teste;

import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaTest {
    Tema t=new Tema(103,"Laborator 3",5,5);

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
    void getID() {
        assertEquals((float)103,(float)t.getID());
    }

    @Test
    void setID() {
        t.setID(100);
        assertEquals((float)100,(float)t.getID());
    }
}