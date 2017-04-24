package gateway;

import java.util.List;

import core.User;

public interface UserGateway {
	boolean save(User u);

	User getUserById(int id);

	List<User> getAllUsers();
}
