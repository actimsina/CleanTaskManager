package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.Task;
import core.User;
import gateway.TaskGateway;

public class DBTaskGateway implements TaskGateway {

	private Connection conn;

	public DBTaskGateway(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean save(Task t) {
		String sql = "insert into tasks(name, user_id) values(?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, t.getName());
			pstmt.setInt(2, t.getUser().getId());

			if (pstmt.executeUpdate() > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();

				if (rs.next())
					t.setId(rs.getInt(1));

				return true;
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Task getById(int id) {
		String sql = "select * from task t, users u where t.user_id=u.id and t.id=?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("u.id"));
				u.setName(rs.getString("u.name"));

				Task task = new Task();
				task.setId(rs.getInt("t.id"));
				task.setName(rs.getString("t.name"));
				task.setUser(u);

				return task;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Task> getAllTasks() {
		String sql = "select * from tasks t, users u where u.id = t.user_id";
		List<Task> tasks = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("u.id"));
				u.setName(rs.getString("u.name"));

				Task t = new Task();
				t.setId(rs.getInt("t.id"));
				t.setName(rs.getString("t.name"));

				t.setUser(u);

				tasks.add(t);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public boolean update(Task t) {
		String sql = "update tasks set name=?, user_id=? where id=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getName());
			pstmt.setInt(2, t.getUser().getId());
			pstmt.setInt(3, t.getId());

			if (pstmt.executeUpdate() > 0) {
				return true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
