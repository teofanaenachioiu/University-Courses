import org.junit.Test;

import static org.junit.Assert.*;

public class NumarComplexTest {

    @Test
    public void getReal() {
        NumarComplex nr=new NumarComplex("3+i");
       // assertEquals();
        assertEquals(3d, nr.getReal(),0);
    }

    @Test
    public void add() {
        NumarComplex nr=new NumarComplex("3+i");
        nr.add(new NumarComplex("i"));
        assertEquals(new NumarComplex("3.0+2.0i"), nr);
    }


}