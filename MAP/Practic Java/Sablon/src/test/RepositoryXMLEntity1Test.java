package test;

import domain.Entity1;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import repository.RepositoryXMLEntity1;
import validation.Validator;
import validation.ValidatorEntity1;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;


class RepositoryXMLEntity1Test {
    private Validator<Entity1> validator=new ValidatorEntity1();
    private RepositoryXMLEntity1 repo=new RepositoryXMLEntity1("./src/test/entity1_test.xml",validator);
    @Test
    private void findAll(){
        assertEquals(0, StreamSupport.stream(repo.findAll().spliterator(),false).count());
    }

}