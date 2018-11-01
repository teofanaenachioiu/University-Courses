package sem4.service;

import sem4.domain.MessageTask;
import sem4.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MessageTaskService {
    CrudRepository<String, MessageTask> crudRepo;

    public MessageTaskService(CrudRepository<String, MessageTask> crudRepo) {
        this.crudRepo = crudRepo;
    }

    <T> Iterable<T> filter(Iterable<T> list, Predicate<T> cond){
        List<T> res=new ArrayList<>();
        list.forEach(x->{
            if(cond.test(x)) res.add(x);
        });
        return res;
    }

    public Iterable<MessageTask> filterBySubject(String subject){
        return filter(crudRepo.findAll(),messageTask -> messageTask.getDescription().contains(subject));
    }


}
