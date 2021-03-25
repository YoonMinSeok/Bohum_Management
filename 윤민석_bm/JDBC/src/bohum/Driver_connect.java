package bohum;
import java.sql.*;

public class Driver_connect {
 public static Connection makeConnection() {
	 String url = "jdbc:mysql://localhost/bohum";
	 String id = "root";
	 String pass = "1234";
	 Connection con = null;
	 
	 try {
		 Class.forName("com.mysql.jdbc.Driver");
		 System.out.println("드라이브 적재 성공");
		 con = DriverManager.getConnection(url, id, pass);
		 System.out.println("데이터 베이스 연결 성공");
	 }catch(ClassNotFoundException e) {
		 System.out.println(e.getMessage());
	 }catch(SQLException e) {
		 System.out.println(e.getMessage());
	 }
	 return con;
 }
}
