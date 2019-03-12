package tasks.repository;


import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;

/**
 * Created by grigo on 3/11/17.
 */
public interface SortingTaskRepository extends ICrudRepository<Integer, SortingTask> {
     Iterable<SortingTask> filterBy(SortingOrder order);
     Iterable<SortingTask> filterBy(SortingAlgorithm algo);
}
