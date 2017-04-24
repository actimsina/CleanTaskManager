package gateway;

import java.util.List;

import core.Task;

public interface TaskGateway {
	boolean save(Task t);

	Task getById(int id);

	List<Task> getAllTasks();

	boolean update(Task t);
}
