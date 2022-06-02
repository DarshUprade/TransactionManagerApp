package test;
import java.sql.*;
public class DBConnection {
		public static Connection con=null;
		static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","darshan","java");
		}catch(Exception e) {e.printStackTrace();}
		}//static block
		public  static Connection getCon() {
			return con;
		}
}
