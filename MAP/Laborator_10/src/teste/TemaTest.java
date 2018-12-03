package teste;

import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaTest {
    Tema t=new Tema("103","Laborator 10","10","6");

    @Test
    void getDescriere() {
        assertEquals("Laborator 10",t.getDescriere());
    }

    @Test
    void setDescriere() {
        t.setDescriere("Laborator 6-9");
        assertEquals("Laborator 6-9",t.getDescriere());
    }

    @Test
    void getDeadline() {
        assertEquals(10,(int)t.getDeadline());
    }

    @Test
    void setDeadline() {
        t.setDeadline("9");
        assertEquals(9,(int)t.getDeadline());
        t.setDeadline("3");
        assertEquals(9,(int)t.getDeadline());
    }

    @Test
    void getDataPredare() {
        assertEquals(6,(int)t.getDataPredare());
    }

    @Test
    void setDataPredare() {
        t.setDataPredare("9");
        assertEquals(9,(int)t.getDataPredare());
        t.setDataPredare("12");
        assertEquals(9,(int)t.getDataPredare());
    }

    @Test
    void getID() {
        assertEquals("103",t.getID());
    }

    @Test
    void setID() {
        t.setID("100");
        assertEquals("100",t.getID());
    }
}