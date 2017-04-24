package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String URI = "jdbc:mysql://192.168.10.10:3306/cleanTM";
	private static final String USER = "homestead";
	private static final String PWD = "secret";

	private static Connection conn;

	private ConnectionFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(URI, USER, PWD);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
