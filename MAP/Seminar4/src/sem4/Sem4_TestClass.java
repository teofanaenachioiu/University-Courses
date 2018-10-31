package sem4;

import sem3.Student;
import sem4.domain.MessageTask;
import sem4.domain.validator.MessageTaskValidator;
import sem4.repository.CrudRepository;
import sem4.repository.InFileMessageTaskRepository;

import java.time.LocalDateTime;

public class Sem4_TestClass {
    public static void main(String[] args) {

        CrudRepository<String, MessageTask> repo =new InFileMessageTaskRepository(
                "./src/data/messages",new MessageTaskValidator());

        for (MessageTask m:repo.findAll()){
            System.out.println(m);
        }
        MessageTask msg1 = new MessageTask("321", "feedback lab 2", "Te-ai descurcat bine", "teacher", "student", LocalDateTime.now());
        if(repo.save(msg1) == null){
            System.out.println("Elementul a fost salvat\n");
        }
        else{
            System.out.println("Exista deja unul cu id-ul" + msg1.getID());
        }
        //System.out.println(msg1.toString());

    }
}
