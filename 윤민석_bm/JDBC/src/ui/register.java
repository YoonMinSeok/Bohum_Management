package ui;

import javax.swing.*;

import bohum.Driver_connect;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class register extends JFrame{
	
	JLabel jl[] = {
			new JLabel("고객코드:"),
			new JLabel("*고객명:"),
			new JLabel("*생년월일(YYYY-MM-DD):"),
			new JLabel("*연락처:"),
			new JLabel("주 소:"),
			new JLabel("회 사:"),
	};
	JPanel jp [] = {
			new JPanel(),
			new JPanel()
	};
	
	JTextField jt[] = {
			new JTextField(10),
			new JTextField(10),
			new JTextField(10),
			new JTextField(10),
			new JTextField(10),
			new JTextField(10)
	};
	JButton jb[] = {
			new JButton("추가"),
			new JButton("닫기")
	};
	
 register(){
	 setTitle("고객 등록");
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 Container c = getContentPane();
	 
	 c.setLayout(new BorderLayout());
	 
	 jp[0].setLayout(new GridLayout(6,2));
	 
	 for(int i=0; i<jt.length; i++) {
		 jp[0].add(jl[i]);
		 jp[0].add(jt[i]);
	 }
	 
	 jp[1].add(jb[0]);
	 jp[1].add(jb[1]);
	 
	 c.add(jp[0], BorderLayout.CENTER);
	 c.add(jp[1], BorderLayout.SOUTH);
	 
	 jt[0].setEnabled(false);
	 
	 jt[2].addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 
			String birth[] = jt[2].getText().split("-");
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR)-2000;
			int hap = Integer.valueOf(birth[0]) +Integer.valueOf(birth[1]) + Integer.valueOf(birth[2]);   
			
			jt[0].setText("S"+ year + hap);
		 }
	 });
	 
	 jb[0].addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 if(jt[1].getText().equals("") || jt[2].getText().equals("") || jt[3].getText().equals("")) {
				 JOptionPane.showMessageDialog(null, "고객등록 에러", "필수항목(*)을 모두 입력하세요" , JOptionPane.ERROR_MESSAGE);
				 
			 }else {
				 try {
					 Connection con = Driver_connect.makeConnection();
					 
					 String sql = "insert into customer values(?,?,?,?,?,?)";
					 
					 PreparedStatement psmt = con.prepareStatement(sql);
					 
					 for(int i=0; i<jt.length; i++) {
						 psmt.setString(i+1, jt[i].getText());
					 }
					 
					 psmt.executeUpdate();
				 }catch(SQLException ee) {
					 System.out.println("SQL 오류!!");
				 }
				 JOptionPane.showMessageDialog(null, "고객추가가 완료되었습니다");
				 for(int i=0; i<jt.length; i++) {
					 jt[i].setText("");
				 }
			 }
		 }
	 });
	 
	 jb[1].addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 dispose();
		 }
	 });
	 
	 setSize(500,400);
	 setVisible(true);
 }
 public static void main(String [] args) {
	 new register();
 }
}
