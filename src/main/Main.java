package main;

import java.util.List;

import core.Task;
import core.User;
import gateway.TaskGateway;
import gateway.UserGateway;
import persistence.ConnectionFactory;
import persistence.DBTaskGateway;
import persistence.DBUserGateway;

public class Main {

	public static void main(String[] args) {
		TaskGateway tg = new DBTaskGateway(ConnectionFactory.getConnection());
		List<Task> tasks = tg.getAllTasks();
		for (Task task : tasks) {
			System.out.println(task.getId() + ": " + task.getName() + ": " + task.getUser().getName());
		}

		User u = new User();
		u.setName("rakus10");

		UserGateway ug = new DBUserGateway(ConnectionFactory.getConnection());
		ug.save(u);

		Task t = new Task();
		t.setName("Going well with persistance");
		t.setUser(u);
		tg.save(t);

		Task t1 = tasks.get(1);

		t1.setName("Read Uncle Bob");
		// tg.update(t1);

		ConnectionFactory.closeConnection();
	}

}
