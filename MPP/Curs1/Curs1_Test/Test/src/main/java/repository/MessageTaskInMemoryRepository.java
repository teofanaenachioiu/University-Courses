package repository;

import domain.MessageTask;
import domain.Validator;

/**
 * Created by camelia on 10/23/2017.
 */
public class MessageTaskInMemoryRepository extends AbstractRepository<String, MessageTask> {
    public MessageTaskInMemoryRepository(Validator<MessageTask> vali) {
        super(vali);
    }
}
