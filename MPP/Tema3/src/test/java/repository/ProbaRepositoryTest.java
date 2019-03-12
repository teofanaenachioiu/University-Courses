package repository;

import model.Categorie;
import model.Proba;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class ProbaRepositoryTest {

    @Test
    public void test() {
        IRepository<Integer, Proba> repo;
        Proba proba=new Proba("tenis", Categorie.CATEGORIE_9_11);
        Proba lastProba;

        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\test\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        repo=new ProbaRepository(prop);


        //size
        assertEquals(5,repo.size());

        //save
        repo.save(proba);
        assertEquals(6,repo.size());
        List<Proba> lista=
                StreamSupport.stream(repo.findAll().spliterator(),false )
                        .collect(Collectors.toList());
        lastProba=lista.get(lista.size()-1);
        proba.setID(lastProba.getID());

        //findone
        assertEquals(proba,repo.findOne(lastProba.getID()));

        //update
        repo.update(lastProba.getID(),new Proba("tenis",Categorie.CATEGORIE_12_15));

        lista=
                StreamSupport.stream(repo.findAll().spliterator(),false )
                        .collect(Collectors.toList());
        lastProba=lista.get(lista.size()-1);

        proba.setCatg(Categorie.CATEGORIE_12_15);
        assertEquals(proba,lastProba);

        repo.delete(lastProba.getID());
    }
}