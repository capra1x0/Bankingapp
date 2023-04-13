import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class LoginPage implements ActionListener {

	JFrame frame = new JFrame();
	
	JLabel headerLabel = new JLabel("Burgies Bank");
	JLabel welcomeLabel = new JLabel("WELCOME BACK");
	JLabel loginLabel = new JLabel("Log into your account");
	JLabel emaiLabel = new JLabel("E-mail:");
	JLabel passwordLabel = new JLabel("Password:");
	JLabel notRegisterd = new JLabel("Noch nicht registriert? ");
	
	JTextField emailField = new JTextField();
	JTextField passwordField = new JPasswordField();
	
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	
	JPanel loginPanel = new JPanel();
	
	int frameWidth = 1920;
	int frameHeight = 1080;
	int panelWidth = frameWidth/3;
	int panelHeight = frameHeight/2;
	
	Database db = new Database();
	
	
	LoginPage() {
		
		try {
            frame.setContentPane(new JLabel((Icon) new ImageIcon(ImageIO.read(new File("D:\\Code\\schule\\java\\BankingApp2\\src\\assets\\bg.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		headerLabel.setFont(new Font("serif", Font.BOLD, 30));
		headerLabel.setBounds((int) (frameWidth/2 - headerLabel.getPreferredSize().getWidth()/2), 20, (int) headerLabel.getPreferredSize().getWidth(), 50);
		
		
		welcomeLabel.setFont(new Font("serif", Font.BOLD, 18));
		welcomeLabel.setBounds((int) (panelWidth/2 - welcomeLabel.preferredSize().getWidth()/2), 35, (int) welcomeLabel.getPreferredSize().getWidth(), 20);
		welcomeLabel.setForeground(Color.gray);
		
		loginLabel.setFont(new Font("serif", Font.BOLD, 30));
		loginLabel.setBounds((int) (panelWidth/2 - loginLabel.getPreferredSize().getWidth()/2), 55, (int) loginLabel.getPreferredSize().getWidth(), 40);
		loginLabel.setForeground(Color.white);
		
		emaiLabel.setFont(new Font("serif", Font.PLAIN , 18));
		emaiLabel.setBounds(50, 140, (int) emaiLabel.getPreferredSize().getWidth(), 20);
		emaiLabel.setForeground(Color.white);
		
		emailField.setBounds(50, 170, panelWidth-100, 40);
		emailField.setBackground(new Color(33, 35, 39));
		emailField.setBorder(new LineBorder(Color.gray, 1));
		emailField.setForeground(Color.gray);
		
		passwordLabel.setFont(new Font("serif", Font.PLAIN , 18));
		passwordLabel.setBounds(50, 240, (int) passwordLabel.getPreferredSize().getWidth(), 20);
		passwordLabel.setForeground(Color.white);
		
		passwordField.setBounds(50, 270, panelWidth-100, 40);
		passwordField.setBackground(new Color(33, 35, 39));
		passwordField.setBorder(new LineBorder(Color.gray, 1));
		passwordField.setForeground(Color.gray);
		
		loginButton.setBounds(50, 360, panelWidth-100, 60);
		loginButton.setBackground(new Color(64, 137, 252));
		loginButton.setFocusable(false);
		loginButton.setBorder(new LineBorder(new Color(64, 137, 252), 1));
		loginButton.setForeground(Color.white);
		loginButton.setFont(new Font("sans_serif", Font.BOLD , 22));
		loginButton.addActionListener(this);
		
		notRegisterd.setFont(new Font("serif", Font.PLAIN, 18));
		notRegisterd.setBounds(50, 430, (int) notRegisterd.getPreferredSize().getWidth(), 40);
		notRegisterd.setForeground(Color.gray);
		
		registerButton.setBounds((int) (notRegisterd.getPreferredSize().getWidth()+45), 430, (int) registerButton.getPreferredSize().getWidth(), 40);
		registerButton.setBackground(new Color(33, 35, 39));
		registerButton.setBorder(BorderFactory.createLineBorder(new Color(33, 35, 39)));
		registerButton.setFont(new Font("serif", Font.PLAIN, 18));
		registerButton.setForeground(Color.white);
		registerButton.setFocusable(false);
		registerButton.addActionListener(this);
		
		
		//Login Label
		loginPanel.add(registerButton);
		loginPanel.add(notRegisterd);
		loginPanel.add(loginButton);
		loginPanel.add(passwordField);
		loginPanel.add(passwordLabel);
		loginPanel.add(emailField);
		loginPanel.add(emaiLabel);
		loginPanel.add(loginLabel);
		loginPanel.add(welcomeLabel);
		loginPanel.setBounds(frameWidth/3, frameHeight/4, frameWidth/3, frameHeight/2);
		loginPanel.setBackground(new Color(33,35,39));
		loginPanel.setLayout(null);
		loginPanel.setVisible(true);
		
		
		
		frame.add(loginPanel);
		frame.add(headerLabel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLayout(null);
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			frame.dispose();
			RegisterPage registerPage = new RegisterPage();
		}
		
		if (e.getSource() == loginButton) {
			String email = emailField.getText();
			String passwd = String.valueOf(((JPasswordField) passwordField).getPassword());
			
			if(db.loginAccount(email, passwd, frame)) {
				ArrayList<String> account = new ArrayList<String>();
				ResultSet res = (ResultSet) db.getAccountInfo(email);
				try {
					if (res.next()) {
						
						try {
							account.add(res.getString(1));
							account.add(res.getString(2));
							account.add(res.getString(3));
							account.add(res.getString(4));
							account.add(res.getString(5));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						InterfacePage iPage = new InterfacePage(account);
						frame.dispose();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
