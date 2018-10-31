package teste;

import domain.Tema;
import org.junit.jupiter.api.Test;
import repository.ValidationException;
import validator.Validator;
import validator.ValidatorTema;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTemaTest {
    Validator<Tema> val=new ValidatorTema();
    Tema t1 =new Tema("0","Laborator 10","10","6");
    Tema t2 =new Tema("103","Laborator 10","18","6");
    Tema t4 =new Tema("103","Laborator 10","10","12");
    Tema t5 =new Tema("103","Laborator 10","10","8");
    @Test
    void validate() {
        //Date corecte
        boolean throww=false;
        try{
            val.validate(t5);
        }
        catch (ValidationException e){
            throww=true;
        }
        assertFalse(throww);

        //Id incorect
        throww=false;
        try{
            val.validate(t1);
        }
        catch (ValidationException e){
            assertEquals("Id incorect!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Id incorect
        throww=false;
        try{
            val.validate(t1);
        }
        catch (ValidationException e){
            assertEquals("Id incorect!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Data incorect
        throww=false;
        try{
            val.validate(t2);
        }
        catch (ValidationException e){
            assertEquals("Data incorecta!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Date incorect
        throww=false;
        try{
            val.validate(t4);
        }
        catch (ValidationException e){
            assertEquals("Date incorecte!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);
    }
}