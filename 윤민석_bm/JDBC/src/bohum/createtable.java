package bohum;

import java.sql.*;

public class createtable {
	createtable(){
		
		Connection con = Driver_connect.makeConnection();
		
		String admin = "create table admin(name varchar(20) not null, passwd varchar(20) not null, position varchar(20) , jumin char(14), inputDate date , primary key(name,passwd))";
		String customer ="create table customer (code char(7) not null, name varchar(20) not null, birth date , tel varchar(20), address varchar(100), company varchar(20), primary key(code,name))";
		String contract = "create table contract (customerCode char(7) not null, contractName varchar(20) not null, regPrice int , regDate date not null, monthPrice int , adminName varchar(20) not null)"; 
		
		try {
			
			Statement st = con.createStatement();
			
			st.executeUpdate(admin);
			st.executeUpdate(customer);
			st.executeUpdate(contract);
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
 public static void main(String [] args) {
	 new createtable();
 }
}
