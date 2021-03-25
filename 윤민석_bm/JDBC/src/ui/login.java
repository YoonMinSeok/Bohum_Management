package ui;

import java.sql.*;
import javax.swing.*;

import bohum.Driver_connect;

import java.awt.*;
import java.awt.event.*;

public class login extends JFrame{
	JPanel jp[] = {
			new JPanel(),
			new JPanel(),
			new JPanel()
	};
	
	JLabel jl[] = {
		new JLabel("관리자 로그인"),
		new JLabel("이름"),
		new JLabel("비밀번호")
	};
	
	JTextField jt= new JTextField(15);
	
	JPasswordField jpt = new JPasswordField(15);
			
	JButton jb[] = {
			new JButton("확인"),
			new JButton("종료")
	};
	
	login(){
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		c.setLayout(new GridLayout(3,1));
		
		jp[0].add(jl[0]);
		jp[1].setLayout(new GridLayout(2,2));
		jp[1].add(jl[1]);
		jp[1].add(jt);
		jp[1].add(jl[2]);
		jp[1].add(jpt);
		for(int i=0; i<jb.length; i++) {
			jp[2].add(jb[i]);
		}
		
		c.add(jp[0]);
		c.add(jp[1]);
		c.add(jp[2]);
		
		jb[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection con = Driver_connect.makeConnection();
					PreparedStatement psmt = con.prepareStatement("select name from admin where name = ? and passwd = ?");
					
					String name = jt.getText();
					String pass = new String(jpt.getPassword());
					
					psmt.setString(1, name);
					psmt.setString(2, pass);
					
					ResultSet rs = psmt.executeQuery();
					
					if(rs.next()) {
						dispose();
						JOptionPane.showMessageDialog(null, "로그인완료");
						new main();
					}else {
						dispose();
						JOptionPane.showMessageDialog(null, "로그인 실패", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					}
				}catch(SQLException ee) {
					System.out.println("SQL오류!!");
				}
				
				
			}
		});
		
		jb[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JOptionPane.showMessageDialog(null, "종료합니다");
			}
		});
		
		setSize(300,200);
		setVisible(true);
	}
 public static void main(String [] args) {
	 new login();
 }
}
