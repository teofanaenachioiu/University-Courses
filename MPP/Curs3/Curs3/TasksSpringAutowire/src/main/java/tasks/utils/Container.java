package tasks.utils;

import tasks.model.Task;

/**
 * Created by grigo on 9/27/16.
 */
 public interface Container {
	Task remove();
	void add(Task task);
	int size();
	boolean isEmpty();
	Iterable<Task> getAll();
}