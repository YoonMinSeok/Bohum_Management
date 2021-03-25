package ui;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import bohum.Driver_connect;

import java.awt.*;
import java.awt.event.*;

public class contract extends JFrame{
	JTable jtable;
	JPanel jp[] = {
			new JPanel(),
			new JPanel(),
			new JPanel(),
			new JPanel()
	};
	
	JLabel jl[] = {
			new JLabel("고객코드:"),
			new JLabel("고객명:"),
			new JLabel("생년월일:"),
			new JLabel("연락처:"),

	};
	
	JLabel jl2[] = {
			new JLabel("보험상품:"),
			new JLabel("가입금액:"),
			new JLabel("월보험료:")
	};
	
	JTextField jt [] = {
			new JTextField(10),
			new JTextField(10),
			new JTextField(10)
	};
	
	JTextField jt2 [] = {
			new JTextField(10),
			new JTextField(10)
	};
	
	JLabel jl3 = new JLabel("담당자:");
	
	JButton jb [] = {
			new JButton("가입"),
			new JButton("삭제"),
			new JButton("파일로저장"),
			new JButton("닫기")
	};
	
	JLabel jl4 = new JLabel("<고객 보험 계약 현황>");
	
	
	JComboBox combo = null;// 고객명
	JComboBox combo3 = null;//담당자
	
	String comboname ;
	
	Vector <String> click = new Vector<String>();//테이블정보 저장하는곳
	Vector<Vector<String>>rowdata = new Vector<Vector<String>>();
	Vector<String>coldata = new Vector<String>();
	
	contract(){
		setTitle("보험계약");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		Vector<String>v = new Vector<String>();
		
		String bohum [] = {
				"무배당암보험",
				"변액연금보험",
				"여성건강보험",
				"연금보험",
				"의료실버보험",
				"종신보험"
		};
		JComboBox combo2 = new JComboBox(bohum);//보험상품
		jt[0].setEnabled(false);
		jt[1].setEnabled(false);
		jt[2].setEnabled(false);
		
		
		
		try {
			
			String sql = "select name from customer";
			
			Connection con = Driver_connect.makeConnection();
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				v.add(rs.getString("name"));
			}
			
			for(int i=0; i<v.size(); i++) {
				combo = new JComboBox(v);
			}
			
		}catch(SQLException e) {
			System.out.println("SQL 오류!!");
		}
		
		jp[0].setLayout(new GridLayout(4,2));
		int j=0;
		
		for(int i=0; i<jl.length; i++) {
			jp[0].add(jl[i]);
			
			if(i==1) {
				jp[0].add(combo);
			}else {
				jp[0].add(jt[j]);
				j++;
			}
		}
		
		jp[1].setLayout(new GridLayout(3,2));
		
		int z =0;
		
		for(int i=0; i<jl2.length; i++) {
			jp[1].add(jl2[i]);
			if(i==0) {
				jp[1].add(combo2);
			}else {
				jp[1].add(jt2[z]);
				z++;
			}
		}
		
		Vector<String>vc = new Vector<String>();
		try {
			
			String sql = "select name from admin";
			
			Connection con = Driver_connect.makeConnection();
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				vc.add(rs.getString("name"));
			}
			
			for(int i=0; i<vc.size(); i++) {
				combo3 = new JComboBox(vc);
			}
			
		}catch(SQLException e) {
			System.out.println("SQL 오류!!");
		}
		
		jp[2].add(jl3);
		jp[2].add(combo3);
		for(int i=0; i<jb.length; i++) {
			jp[2].add(jb[i]);
		}
		
		
		
		comboname= combo.getSelectedItem().toString();
		
		try {
			
			String sql = "select * from customer where name = '"+ comboname+"'";
			Vector <String>code = new Vector<String>();
			Connection con = Driver_connect.makeConnection();
			PreparedStatement psmt = con.prepareStatement(sql);
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				code.add(rs.getString("code"));
				code.add(rs.getString("birth"));
				code.add(rs.getString("tel"));
			}
			
			for(int i = 0; i<code.size(); i++) {
				jt[i].setText(code.get(i));
			}
		}catch(SQLException ee) {
			System.out.println(ee.getErrorCode());
		}
		
		
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector <String>code = new Vector<String>();
				comboname = combo.getSelectedItem().toString();
				try {
					
					String sql = "select * from customer where name = '"+ comboname +"'";
					
					Connection con = Driver_connect.makeConnection();
					PreparedStatement psmt = con.prepareStatement(sql);
					
					ResultSet rs = psmt.executeQuery(sql);
					
					while(rs.next()) {
						code.add(rs.getString("code"));
						code.add(rs.getString("birth"));
						code.add(rs.getString("tel"));
					}
					
					for(int i = 0; i<code.size(); i++) {
						jt[i].setText(code.get(i));
					}
					
					
				}catch(SQLException ee) {
					System.out.println("SQL오류!!");
				}
				
				try {
					rowdata.clear();
					
					String sql = "select * from contract where customerCode = '"+jt[0].getText()+"'";
					Connection con = Driver_connect.makeConnection();
					
					PreparedStatement psmt = con.prepareStatement(sql);
					
					ResultSet rs = psmt.executeQuery(sql);
					
					while(rs.next()) {
						
						Vector<String>vv = new Vector<String>();
						
						vv.add(rs.getString(1));
						vv.add(rs.getString(2));
						vv.add(rs.getString(3));
						vv.add(rs.getString(4));
						vv.add(rs.getString(5));
						vv.add(rs.getString(6));
						
						rowdata.add(vv);
						jtable.updateUI();
				}
				}catch(SQLException ee) {
					System.out.println("SQL오류!!");
				}
			}
		});
		
		

		coldata.add("customerCode");
		coldata.add("contractName");
		coldata.add("regPrice");
		coldata.add("regDate");
		coldata.add("monthPrice");
		coldata.add("adminName");
		
		DefaultTableModel model = new DefaultTableModel(rowdata,coldata);
		jtable = new JTable(model);
		
		JScrollPane jps = new JScrollPane(jtable);
		
		JPanel gogak = new JPanel();
		JPanel damdan = new JPanel(new BorderLayout());
		
		damdan.add(jp[2], BorderLayout.NORTH);
		jl4.setHorizontalAlignment(JLabel.CENTER);
		damdan.add(jl4, BorderLayout.CENTER);
		
		gogak.add(damdan, BorderLayout.NORTH);
		gogak.add(jps, BorderLayout.CENTER);
		
		jp[3].add(jp[0], BorderLayout.WEST);
		jp[3].add(jp[1], BorderLayout.EAST);
		
		
		
		jtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int select = jtable.getSelectedRow();
				click = rowdata.get(select);
			}
		});
		
		try {
			rowdata.clear();
			
			String sql = "select * from contract where customerCode = '"+jt[0].getText()+"'";
			Connection con = Driver_connect.makeConnection();
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			ResultSet rs = psmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Vector<String> vs = new Vector<String>();
				
				vs.add(rs.getString(1));
				vs.add(rs.getString(2));
				vs.add(rs.getString(3));
				vs.add(rs.getString(4));
				vs.add(rs.getString(5));
				vs.add(rs.getString(6));
				
				rowdata.add(vs);
				jtable.updateUI();
			}

		}catch(SQLException e) {
			System.out.println("SQL 오류!!");
		}
		
		jb[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal = Calendar.getInstance();  
				
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);		
				int day = cal.get(Calendar.DATE);
				
				String clock = year + "-" + month + "-" + day;
				
				String code = jt[0].getText();
				String cname = combo2.getSelectedItem().toString();
				String rprice = jt2[0].getText();
				String date = clock;
				String mprice = jt2[1].getText();
				String aname = combo3.getSelectedItem().toString();
				
				String sql = "insert into contract values(?,?,?,?,?,?)";
				try {
					
					Connection con = Driver_connect.makeConnection();
					PreparedStatement psmt = con.prepareStatement(sql);
					
					psmt.setString(1, code);
					psmt.setString(2, cname);
					psmt.setString(3, rprice);
					psmt.setString(4, date);
					psmt.setString(5, mprice);
					psmt.setString(6, aname);
					
					int re = psmt.executeUpdate();
					
					if(re==1) {
						JOptionPane.showMessageDialog(null, "정상등록하였습니다");
					}else {
						JOptionPane.showMessageDialog(null, "등록 실패 하였습니다.");
					}
					
					jt2[0].setText("");
					jt2[1].setText("");
				}catch(SQLException ee) {
					System.out.println(ee.getMessage());
				}
			}
		});
		
		jb[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int select = jtable.getSelectedRow();
					Connection con = Driver_connect.makeConnection();
					
					String sql = "delete from contract where adminName ='"+click.get(5)+"' ";
					int result= JOptionPane.showConfirmDialog(null, click.get(0)+"(의료실비 보험)을 삭제하시겠습니까?", "개인정보 삭제", JOptionPane.OK_CANCEL_OPTION);
				
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
				}catch(SQLException ee) {
					System.out.println("SQL오류!!");
					}
				 jtable.updateUI();
				}
		});
		jb[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				f.setSize(350,250);
				f.setLayout(null);
				f.setVisible(false);
				FileDialog dialog = new FileDialog(f, "텍스트 파일로 저장하기", FileDialog.SAVE);
				dialog.setVisible(true);
				
				String path = dialog.getDirectory() + dialog.getFile();
							
				try {
					
					String sql = "select * from contract where customerCode = '"+jt[0].getText()+"'";;
					
					Vector<String> vv = new Vector<String>();
					
					Connection con = Driver_connect.makeConnection();
					Statement st = con.createStatement();
					
					ResultSet rs = st.executeQuery(sql);
					
					while(rs.next()) {

						vv.add(rs.getString(2));
						vv.add(rs.getString(3));
						vv.add(rs.getString(4));
						vv.add(rs.getString(5));
					}
					
					FileWriter w = new FileWriter(path);
					
					w.write("고객명 : "+combo.getSelectedItem().toString() + jt[0].getText()+"\r\n\r\n");
					w.write("담당자명 : " + combo3.getSelectedItem().toString() + "\r\n\r\n");
					w.write("보험상품" + "\t" + "가입금액" + "\t\t" + "가입일" + "\t\t" + "월보험료" + "\r\n");
					int i = 0;
					while(i<vv.size()) {	
							
							if(i%4==0 && i>0) {
								w.write("\r\n");
								w.write(vv.get(i));
							}else {
								w.write(vv.get(i)+"\t");
							}
						
						i++;
					}
					w.close();
					
				}catch(Exception ee) {
					System.out.println(ee.getMessage());
				}
			}
		});
		
		jb[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		c.add(jp[3], BorderLayout.NORTH);
		c.add(gogak, BorderLayout.CENTER);
		
		jtable.updateUI();
		
		setSize(600,600);
		setVisible(true);
	}
 public static void main(String [] args) {
	 new contract();
 }
}
