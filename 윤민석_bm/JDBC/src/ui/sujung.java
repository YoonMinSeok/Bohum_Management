package ui;

import javax.swing.*;

import bohum.Driver_connect;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class sujung extends JFrame{
	Vector<String> v = new Vector<String>();
	JPanel jp [] = {
			new JPanel(),
			new JPanel()
	};
	
	JLabel jl[] = {
			new JLabel("고객코드: "),
			new JLabel("고객 명: "),
			new JLabel("생년월일: "),
			new JLabel("연락처: "),
			new JLabel("주 소: "),
			new JLabel("회사명: ")
	};
	
	JTextField jtf[] = {
			new JTextField(10),
			new JTextField(10),
			new JTextField(10),
			new JTextField(10),
			new JTextField(10),
			new JTextField(10)
	};
	JButton jb[] = {
			new JButton("수정"),
			new JButton("취소")
	};
	sujung(Vector <String>v) {
		this.v = v;
		
		setTitle("고객수정");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		jp[0].setLayout(new GridLayout(6,2));
		for(int i=0; i<jtf.length; i++) {
			jp[0].add(jl[i]);
			jp[0].add(jtf[i]);
		}
		
		
		jp[1].add(jb[0]);
		jp[1].add(jb[1]);
		
		jtf[0].setEnabled(false);
		jtf[1].setEnabled(false);
		
		for(int i=0; i<v.size(); i++) {
			jtf[i].setText(v.get(i));
		}
		
		c.add(jp[0] , BorderLayout.CENTER);
		c.add(jp[1], BorderLayout.SOUTH);
		
		jb[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "update customer set birth = ? , tel = ? , address = ? , company = ? where name= '"+jtf[1].getText()+"' ";
				
				try {
					
					Connection con = Driver_connect.makeConnection();
					PreparedStatement psmt = con.prepareStatement(sql);
					
					Vector<String> v = new Vector<String>();
					
					for(int i=2; i<jtf.length; i++) {
						v.add(jtf[i].getText());
					}
					
					
					psmt.setString(1, v.get(0));
					psmt.setString(2, v.get(1));
					psmt.setString(3, v.get(2));
					psmt.setString(4, v.get(3));
					
					
					int re = psmt.executeUpdate();
					
					if(re>0) {
						JOptionPane.showMessageDialog(null, "수정완료", "메시지", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "수정실패", "고객수정 에러", JOptionPane.ERROR_MESSAGE);
					}
					
				}catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "고객수정 에러", "수정실패", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		jb[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		setSize(400,300);
		setVisible(true);
	}
}
