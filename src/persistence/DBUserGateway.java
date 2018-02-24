package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import core.User;
import gateway.UserGateway;

public class DBUserGateway implements UserGateway {

	private Connection conn;

	public DBUserGateway(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean save(User u) {
		String sql = "insert into users(name) values(?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, u.getName());

			if (pstmt.executeUpdate() > 0) {

				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next())
					u.setId(rs.getInt(1));

				return true;
			}

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public User getUserById(int id) {
		String sql = "select * from users where id=?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				return user;
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<User> getAllUsers() {
		String sql = "select * from users";
		List<User> users = new ArrayList<>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				users.add(u);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
}
