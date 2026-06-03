package bd_conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Connect_bd {
	
	private static final String user ="postgres";
	
	private static final String pass = "xxxxxxxx";
	
	private static final String url = "jdbc:postgresql://localhost:5432/processosN";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,user,pass); 
		
	}
	
}


