package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import java.util.*;

import bohum.Driver_connect;

public class johe extends JFrame{
	JPanel jp = new JPanel();
	JLabel jl = new JLabel("성명");
	JTextField jtf = new JTextField(10);
	JButton jb[] = {
		new JButton("조회"),
		new JButton("전체보기"),
		new JButton("수정"),
		new JButton("삭제"),
		new JButton("닫기")
	};
	
	Vector <String> v = new Vector<String>();
	
	
	johe(){
		setTitle("고객 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		
		Vector<Vector<String>> rowdata = new Vector<Vector<String>>();
		Vector<String> coldata = new Vector<String>();

		coldata.add("code");
		coldata.add("name");
		coldata.add("birth");
		coldata.add("tel");
		coldata.add("address");
		coldata.add("company");
		
		DefaultTableModel model = new DefaultTableModel(rowdata,coldata);
		JTable jt = new JTable(model);
		
		JScrollPane jps = new JScrollPane(jt);
		
		jp.add(jl);
		jp.add(jtf);
		
		for(int i=0; i<jb.length; i++) {
			jp.add(jb[i]);
		}
		
		
		c.add(jp, BorderLayout.NORTH);
		c.add(jps, BorderLayout.CENTER);
		
		jt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int select = jt.getSelectedRow();
				v = rowdata.get(select);
			}
		});
		

		jb[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = jtf.getText();
				
				String sql = "select * from customer where name like '"+ name + "%'";
				
				try {
					
					Connection con = Driver_connect.makeConnection();
					Statement st = con.createStatement();
					
					rowdata.clear();
					ResultSet rs =  st.executeQuery(sql);
					
					while(rs.next()) {
						Vector <String> vc= new Vector<String>();
						
						for(int i=0; i<coldata.size(); i++) {
							vc.add(rs.getString(i+1));
						}
						
						rowdata.add(vc);
						jt.updateUI();
					}
					
					
				}catch(SQLException ee) {
					System.out.println(ee.getMessage());
				}
				
			}
		});
		
		jb[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String sql = "select * from customer";
				try {
					
					Connection con = Driver_connect.makeConnection();
					Statement st = con.createStatement();
					
					rowdata.clear();
					ResultSet rs = st.executeQuery(sql);
					
					while(rs.next()) {
						Vector<String> v = new Vector<String>();
						
						for(int i=0; i<coldata.size(); i++) {
							v.add(rs.getString(i+1));
						}
						rowdata.add(v);
						jt.updateUI();
					}
				}catch (SQLException ee) {
					System.out.println("sql오류!!");
					}
			}
		});
		
		jb[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new sujung(v);
			}
		});
		
		jb[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int select = jt.getSelectedRow();
				Vector <String> v = new Vector<String>();
				
				v = rowdata.get(select);
				
				String sql = "delete from customer where name ='" +v.get(1) + "' ";
				
				Connection con = Driver_connect.makeConnection();
				
				try {
					
					
					
					if(select== -1)return;
					else {
						int result =JOptionPane.showConfirmDialog(null, v.get(1)+"님을 정말 삭제하시겠습니까?" , "고객정보 삭제" , JOptionPane.OK_CANCEL_OPTION);
						if(result == JOptionPane.CANCEL_OPTION) {
							return;
						}else if(result == JOptionPane.OK_OPTION){
							
							Statement st = con.createStatement();
							int rs = st.executeUpdate(sql);
							
							if(rs>0) {
								JOptionPane.showMessageDialog(null, "삭제완료", "메시지", JOptionPane.INFORMATION_MESSAGE);
							}
							model.removeRow(select);
						}
						
					}
					
					jt.updateUI();
					
				}catch(SQLException ee) {
					System.out.println(ee.getMessage());
				}
				
			}
		});
		
		jb[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		
		setSize(600,600);
		setVisible(true);
	}
 public static void main(String [] args) {
	 new johe();
 }
}
