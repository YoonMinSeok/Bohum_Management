package bohum;

import java.sql.*;


public class createbohum {
	createbohum(){
		String sql = "create database bohum";
		Connection con = Driver_connect.makeConnection();
		Statement st = null;
		
		try {
			
			st = con.createStatement();
			st.executeUpdate(sql);
			System.out.println("database 만들기 성공!!");
			

		}catch(SQLException e) {
			System.out.println("SQL오류 !!");
		}
		
	};
	public static void main(String [] args) {
		new createbohum();
	};
}
