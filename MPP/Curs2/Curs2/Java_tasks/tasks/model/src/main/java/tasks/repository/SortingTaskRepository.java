package tasks.repository;

import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;

/**
 * Created by grigo on 11/14/16.
 */
public class SortingTaskRepository extends AbstractRepository<Integer, SortingTask> {

    public SortingTaskRepository(Validator<SortingTask> val){
        super(val);

        save(new SortingTask(1,"aaa", SortingAlgorithm.BUBBLE_SORT, SortingOrder.Ascending,100));
        save(new SortingTask(2,"bbb", SortingAlgorithm.QUICK_SORT, SortingOrder.Ascending,300));
        save(new SortingTask(3,"ccc", SortingAlgorithm.SELECTION_SORT, SortingOrder.Descending,400));
        save(new SortingTask(4,"ffff", SortingAlgorithm.SELECTION_SORT, SortingOrder.Descending,1000));
    }

}
