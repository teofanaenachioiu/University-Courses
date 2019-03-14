package repository;

import model.TipUser;
import model.User;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    @Test
    public void test(){
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("D:\\University-Courses\\MPP\\Laborator1\\src\\test\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepositoryUser repo=new UserRepository(prop);

        User user=new User("ioana_l","hashhash");

        //size
        assertEquals(3,repo.size());

        //save
        repo.save(user);
        assertEquals(4,repo.size());

        //find
        assertNull(repo.findOne("teofana"));
        assertEquals(user,repo.findOne("ioana_l"));

        //update
        user.setTip(TipUser.OPERATOR);
        repo.update("ioana_l",new User("ioana_l","hashhash",TipUser.OPERATOR));

        List<User> lista= StreamSupport.stream(repo.findAll().spliterator(),false)
                .collect(Collectors.toList());
        User lastUser=lista.get(lista.size()-1);
        assertEquals(user,lastUser);

        //delete
        repo.delete("ioana_l");
        assertEquals(3,repo.size());

    }


}