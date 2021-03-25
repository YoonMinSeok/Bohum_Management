package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main extends JFrame{
	JPanel jp[] = {
			new JPanel(),
			new JPanel()
	};
	JButton jb[] = {
			new JButton("고객 등록"),
			new JButton("고객 조회"),
			new JButton("계약 관리"),
			new JButton("종 료")
	};
	
	ImageIcon img = new ImageIcon("C:\\Users\\sjcom2_6\\Desktop\\윤민석_bm\\2016 보험문제\\Datafiles\\img.jpg");
	JLabel imglabel = new JLabel(img);
 main(){
	 setTitle("보험계약 관리화면");
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 Container c = getContentPane();
	 
	 c.setLayout(new BorderLayout());
	 
	 for(int i=0; i<jb.length; i++) {
		 jp[0].add(jb[i]);
	 }
	 
	 jp[1].add(imglabel);
	
	 c.add(jp[0], BorderLayout.NORTH);
	 c.add(jp[1], BorderLayout.CENTER);
	 
	 jb[0].addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 new register();
		 }
	 });
	 
	 jb[1].addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 new johe();
		 }
	 });
	 
	 jb[2].addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 new contract();
		 }
	 });
	 
	 setSize(500,450);
	 setVisible(true);
 }
 public static void main(String [] args) {
	 new main();
 }
}
