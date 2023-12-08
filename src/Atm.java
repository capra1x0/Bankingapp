import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import db.Database;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import javax.swing.JSlider;


public class Atm implements ActionListener {
	JFrame frame = new JFrame();
	
	JButton btn7 = new JButton();
	JButton btn8 = new JButton();
	JButton btn9 = new JButton();
	JButton btn4 = new JButton();
	JButton btn5 = new JButton();
	JButton btn6 = new JButton();
	JButton btn1 = new JButton();
	JButton btn2 = new JButton();
	JButton btn3 = new JButton();
	JButton btn0 = new JButton();
	JButton btnLoeschen = new JButton();
	JButton btnEinzahlen = new JButton();
	JButton btnAuszahlen = new JButton();
	
	JLabel loeschen = new JLabel("Löschen");
	JLabel einzahlen = new JLabel("Einzahlen");
	JLabel auszahlen = new JLabel("Auszahlen");
	
	JPanel redPanel = new JPanel();
	JPanel yelloePanel = new JPanel();
	JPanel greenPanel = new JPanel();
	
	String initialAmountString = "Gib einen Betrag ein";
	String amountString = "";
	
	JLabel amount = new JLabel(initialAmountString);
	
	Database db = new Database();
	private ArrayList<String> account;
	
	JFrame interfaceFrame = new JFrame();
	
	Atm(ArrayList<String> account, JFrame interfaceFrame) {
		this.account = account;
		this.interfaceFrame = interfaceFrame;
		
		try {
            frame.setContentPane(new JLabel((Icon) new ImageIcon(ImageIO.read(new File("D:\\Code\\schule\\java\\BankingApp2\\src\\assets\\atmnew3.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		btn7.setBounds(278, 705, 22, 20);
		btn7.setBackground(Color.orange);
		btn7.setOpaque(false);
		btn7.setContentAreaFilled(false);
		btn7.setBorderPainted(false);
		btn7.addActionListener(this);
		
		btn8.setBounds(304, 705, 22, 20);
		btn8.setBackground(Color.orange);
		btn8.setOpaque(false);
		btn8.setContentAreaFilled(false);
		btn8.setBorderPainted(false);
		btn8.addActionListener(this);
		
		btn9.setBounds(329, 705, 22, 20);
		btn9.setBackground(Color.orange);
		btn9.setOpaque(false);
		btn9.setContentAreaFilled(false);
		btn9.setBorderPainted(false);
		btn9.addActionListener(this);
		
		btn4.setBounds(278, 730, 22, 20);
		btn4.setBackground(Color.orange);
		btn4.setOpaque(false);
		btn4.setContentAreaFilled(false);
		btn4.setBorderPainted(false);
		btn4.addActionListener(this);
		
		btn5.setBounds(303, 730, 22, 20);
		btn5.setBackground(Color.orange);
		btn5.setOpaque(false);
		btn5.setContentAreaFilled(false);
		btn5.setBorderPainted(false);
		btn5.addActionListener(this);
		
		btn6.setBounds(329, 730, 22, 20);
		btn6.setBackground(Color.orange);
		btn6.setOpaque(false);
		btn6.setContentAreaFilled(false);
		btn6.setBorderPainted(false);
		btn6.addActionListener(this);
		
		btn1.setBounds(275, 755, 22, 20);
		btn1.setBackground(Color.orange);
		btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
		btn1.addActionListener(this);
		
		btn2.setBounds(302, 755, 22, 20);
		btn2.setBackground(Color.orange);
		btn2.setOpaque(false);
		btn2.setContentAreaFilled(false);
		btn2.setBorderPainted(false);
		btn2.addActionListener(this);
		
		btn3.setBounds(327, 755, 22, 20);
		btn3.setBackground(Color.orange);
		btn3.setOpaque(false);
		btn3.setContentAreaFilled(false);
		btn3.setBorderPainted(false);
		btn3.addActionListener(this);
		
		btn0.setBounds(275, 778, 22, 20);
		btn0.setBackground(Color.orange);
		btn0.setOpaque(false);
		btn0.setContentAreaFilled(false);
		btn0.setBorderPainted(false);
		btn0.addActionListener(this);
		
		btnLoeschen.setBounds(365, 707, 50, 18);
		btnLoeschen.setOpaque(false);
		btnLoeschen.setContentAreaFilled(false);
		btnLoeschen.setBorderPainted(false);
		btnLoeschen.addActionListener(this);
		
		btnAuszahlen.setBounds(365, 731, 50, 18);
		btnAuszahlen.setOpaque(false);
		btnAuszahlen.setContentAreaFilled(false);
		btnAuszahlen.setBorderPainted(false);
		btnAuszahlen.addActionListener(this);
		
		btnEinzahlen.setBounds(365, 755, 50, 18);
		btnEinzahlen.setOpaque(false);
		btnEinzahlen.setContentAreaFilled(false);
		btnEinzahlen.setBorderPainted(false);
		btnEinzahlen.addActionListener(this);
		
		
		amount.setBounds(200, 350, 300, 40);
		amount.setFont(new Font("Serif", Font.BOLD, 20));
		
		redPanel.setBounds(200, 490, 25, 25);
		redPanel.setBackground(Color.red);
		
		loeschen.setBounds(230, 492, 50, 20);
		
		yelloePanel.setBounds(290, 490, 25, 25);
		yelloePanel.setBackground(Color.yellow);
		
		auszahlen.setBounds(320, 492, 60, 20);
		
		greenPanel.setBounds(390, 490, 25, 25);
		greenPanel.setBackground(Color.green);
		
		einzahlen.setBounds(420, 492, 60, 20);
		
		
		
		frame.add(btn8);
		frame.add(btn7);
		frame.add(btn9);
		frame.add(btn4);
		frame.add(btn5);
		frame.add(btn6);
		frame.add(btn1);
		frame.add(btn2);
		frame.add(btn3);
		frame.add(btn0);
		
		frame.add(btnLoeschen);
		frame.add(btnAuszahlen);
		frame.add(btnEinzahlen);
		
		frame.add(amount);
		
		frame.add(redPanel);
		frame.add(loeschen);
		frame.add(yelloePanel);
		frame.add(auszahlen);
		frame.add(greenPanel);
		frame.add(einzahlen);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 1000);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn7) {
			System.out.println("working 7");
			amountString = amountString + "7";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn8) {
			System.out.println("working 8");
			amountString = amountString + "8";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn9) {
			System.out.println("working 9");
			amountString = amountString + "9";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn4) {
			System.out.println("working 4");
			amountString = amountString + "4";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn5) {
			System.out.println("working 5");
			amountString = amountString + "5";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn6) {
			System.out.println("working 6");
			amountString = amountString + "6";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn1) {
			System.out.println("working 1");
			amountString = amountString + "1";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn2) {
			System.out.println("working 2");
			amountString = amountString + "2";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn3) {
			System.out.println("working 3");
			amountString = amountString + "3";
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btn0) {
			System.out.println("working 0");
			if(amountString == "") {
				amountString = amountString;
			} else {
				amountString = amountString + "0";
			}
			amount.setText(amountString + "€");
		}
		if(e.getSource() == btnLoeschen) {
			amountString = "";
			amount.setText(initialAmountString);
		}
		
		
		if(e.getSource() == btnAuszahlen) {
			db.withdraw(account.get(1), account.get(0), amountString, Integer.valueOf(account.get(3)));
			interfaceFrame.dispose();
			ResultSet res = (ResultSet) db.getAccountInfo(account.get(4));
			ArrayList<String> account = new ArrayList<String>();
			try {
				if (res.next()) {
					
					try {
						account.add(res.getString(1));
						account.add(res.getString(2));
						account.add(res.getString(3));
						account.add(res.getString(4));
						account.add(res.getString(5));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					
					InterfacePage iPage = new InterfacePage(account);
					frame.dispose();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		if(e.getSource() == btnEinzahlen) {
			db.deposit(account.get(1), account.get(0), amountString, Integer.valueOf(account.get(3)));
			interfaceFrame.dispose();
			ResultSet res = (ResultSet) db.getAccountInfo(account.get(4));
			ArrayList<String> account = new ArrayList<String>();
			try {
				if (res.next()) {
					
					try {
						account.add(res.getString(1));
						account.add(res.getString(2));
						account.add(res.getString(3));
						account.add(res.getString(4));
						account.add(res.getString(5));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					
					InterfacePage iPage = new InterfacePage(account);
					frame.dispose();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
