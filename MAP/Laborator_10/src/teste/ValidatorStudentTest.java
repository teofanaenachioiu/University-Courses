package teste;

import domain.Student;
import repository.ValidationException;
import org.junit.jupiter.api.Test;
import validator.ValidatorStudent;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorStudentTest {
    ValidatorStudent val=new ValidatorStudent();
    Student s1=new Student("9999","Teofana","223","teoffa@yahoo.com","Adriana Guran");
    Student s2=new Student("0","Teofana","223","teoffa@yahoo.com","Adriana Guran");
    Student s3=new Student("9999","Enachioiu T30F4N4","223","teoffa@yahoo.com","Adriana Guran");
    Student s4=new Student("9999","Teofana","203","teoffa@yahoo.com","Adriana Guran");
    Student s5=new Student("9999","Enachioiu Teofana","223","teoffa@yahoocom","Adriana Guran");
    Student s6=new Student("9999","Enachioiu Teofana","223","teoffa@yahoo.com","Adriana Guran!!");

    @Test
    void validate() {
        //Date corecte
        boolean throww=false;
        try{
            val.validate(s1);
        }
        catch (ValidationException e){
            throww=true;
        }
        assertFalse(throww);

        //Id incorect
        throww=false;
        try{
            val.validate(s2);
        }
        catch (ValidationException e){
            assertEquals("Id incorect!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Nume incorect
        throww=false;
        try{
            val.validate(s3);
        }
        catch (ValidationException e){
            assertEquals("Nume incorect!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Grupa incorecta
        throww=false;
        try{
            val.validate(s4);
        }
        catch (ValidationException e){
            assertEquals("Grupa incorecta!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Email incorect
        throww=false;
        try{
            val.validate(s5);
        }
        catch (ValidationException e){
            assertEquals("Email incorect!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);

        //Indrumator lab
        throww=false;
        try{
            val.validate(s6);
        }
        catch (ValidationException e){
            assertEquals("Nume incorect!",e.getMessage());
            throww=true;
        }
        assertTrue(throww);
    }
}