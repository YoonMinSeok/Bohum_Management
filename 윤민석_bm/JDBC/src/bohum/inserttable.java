package bohum;

import java.sql.*;
import java.util.*;
import java.io.*;

public class inserttable {
	inserttable(){
		
		Connection con = Driver_connect.makeConnection();
		PreparedStatement psmt = null;
		
		String text []= {
				"insert into admin values(?,?,?,?,?)",
				"insert into customer values(?,?,?,?,?,?)",
				"insert into contract values(?,?,?,?,?,?)"
		};
		
		String name[] = {
			"admin","customer","contract"
		};
		
		for(int i=0; i<name.length; i++) {
			try {
			
				Scanner scanner = new Scanner(new FileInputStream("C:\\Users\\sjcom2_6\\Desktop\\윤민석_bm\\2016 보험문제\\Datafiles\\"+ name[i]+ ".txt"));
				
				StringTokenizer stt = new StringTokenizer(scanner.nextLine());
				
				String line[] = new String[stt.countTokens()];
				psmt = con.prepareStatement(text[i]);
				
				while(scanner.hasNext()) {
					
					stt = new StringTokenizer(scanner.nextLine());
					
					for(int j=0; j<line.length; j++) {
						line[j] = stt.nextToken();
						psmt.setString(j+1, line[j]);
					}
					psmt.executeUpdate();
				}
			
			}catch(IOException e) {
				System.out.println("파일 오류!!");
			}catch(SQLException e) {
				System.out.println("SQL 오류!!");
			}
		}
	}
 public static void main(String [] args) {
	 new inserttable();
 }
}
