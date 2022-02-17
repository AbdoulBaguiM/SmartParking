package dao;
import java.sql.*;

public class Connection {

	//Private variables for username, password etc
	private static Connection instance = null;
	private static final String PASSWORD = "";
	private static final String USERNAME = "root";
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/JFX_SMP";

	/**
	 * private constructor used for singleton pattern
	 */
	private Connection() {
	}

	/**
	 * get the single instance of the Connection Object
	 * @return Connection Object
	 */
	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
		}
		return instance;
	}

	/**
	 * Used to create a JDBC connection
	 * @return returns the Connection Created
	 * @throws Exception Throws SQLException and other exceptions
	 */
	public java.sql.Connection getConnection() throws Exception {
		Class.forName(JDBC_DRIVER);
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}