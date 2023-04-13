import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.Database;

public class RegisterPage implements ActionListener {
	
	JFrame frame = new JFrame();
	JLabel headerLabel = new JLabel("Burgies Bank");
	JLabel welcomeLabel = new JLabel("WELCOME");
	JLabel loginLabel = new JLabel("Register your account");
	JLabel emaiLabel = new JLabel("E-mail:");
	JLabel passwordLabel = new JLabel("Passwort:");
	JLabel notRegisterd = new JLabel("Schon registriert? ");
	JLabel confirmpasswd = new JLabel("Passwort bestätigen:");
	JLabel nameLabel = new JLabel("Vorname:");
	JLabel nachnameLabel = new JLabel("Nachname:");
	
	JTextField confirmpasswdField = new JPasswordField();
	JTextField emailField = new JTextField();
	JTextField passwordField = new JPasswordField();
	JTextField nameField = new JTextField();
	JTextField nachnameField = new JTextField();
	
	JButton loginButton = new JButton("Register");
	JButton registerButton = new JButton("Login");
	
	JPanel loginPanel = new JPanel();
	
	int frameWidth = 1920;
	int frameHeight = 1080;
	int panelWidth = frameWidth/3;
	int panelHeight = frameHeight/2;
	
	
	RegisterPage() {
		
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
		
		//Vorname
		nameLabel.setFont(new Font("serif", Font.PLAIN , 18));
		nameLabel.setBounds(50, 140, (int) nameLabel.getPreferredSize().getWidth(), 20);
		nameLabel.setForeground(Color.white);
		
		nameField.setBounds(50, 170, panelWidth/2-75, 40);
		nameField.setBackground(new Color(33, 35, 39));
		nameField.setBorder(new LineBorder(Color.gray, 1));
		nameField.setForeground(Color.gray);
		
		//Nachname
		nachnameLabel.setFont(new Font("serif", Font.PLAIN , 18));
		nachnameLabel.setBounds(panelWidth/2+25, 140, (int) nachnameLabel.getPreferredSize().getWidth(), 20);
		nachnameLabel.setForeground(Color.white);
		
		nachnameField.setBounds(panelWidth/2+25, 170, panelWidth/2-75, 40);
		nachnameField.setBackground(new Color(33, 35, 39));
		nachnameField.setBorder(new LineBorder(Color.gray, 1));
		nachnameField.setForeground(Color.gray);
		
		//Email
		emaiLabel.setFont(new Font("serif", Font.PLAIN , 18));
		emaiLabel.setBounds(50, 340, (int) emaiLabel.getPreferredSize().getWidth(), 20);
		emaiLabel.setForeground(Color.white);
		
		emailField.setBounds(50, 370, panelWidth-100, 40);
		emailField.setBackground(new Color(33, 35, 39));
		emailField.setBorder(new LineBorder(Color.gray, 1));
		emailField.setForeground(Color.gray);
		
		
		//Password
		passwordLabel.setFont(new Font("serif", Font.PLAIN , 18));
		passwordLabel.setBounds(50, 240, (int) passwordLabel.getPreferredSize().getWidth(), 20);
		passwordLabel.setForeground(Color.white);
		
		passwordField.setBounds(50, 270, panelWidth/2-75, 40);
		passwordField.setBackground(new Color(33, 35, 39));
		passwordField.setBorder(new LineBorder(Color.gray, 1));
		passwordField.setForeground(Color.gray);
		
		
		//Confirm Password
		confirmpasswd.setFont(new Font("serif", Font.PLAIN , 18));
		confirmpasswd.setBounds(panelWidth/2+25, 240, (int) confirmpasswd.getPreferredSize().getWidth(), 20);
		confirmpasswd.setForeground(Color.white);
		
		confirmpasswdField.setBounds(panelWidth/2+25, 270, panelWidth/2-75, 40);
		confirmpasswdField.setBackground(new Color(33, 35, 39));
		confirmpasswdField.setBorder(new LineBorder(Color.gray, 1));
		confirmpasswdField.setForeground(Color.gray);
		
		
		//Buttons
		loginButton.setBounds(50, 460, panelWidth-100, 60);
		loginButton.setBackground(new Color(64, 137, 252));
		loginButton.setFocusable(false);
		loginButton.setBorder(new LineBorder(new Color(64, 137, 252), 1));
		loginButton.setForeground(Color.white);
		loginButton.setFont(new Font("sans_serif", Font.BOLD , 22));
		loginButton.addActionListener(this);
		
		notRegisterd.setFont(new Font("serif", Font.PLAIN, 18));
		notRegisterd.setBounds(50, 530, (int) notRegisterd.getPreferredSize().getWidth(), 40);
		notRegisterd.setForeground(Color.gray);
		
		registerButton.setBounds((int) (notRegisterd.getPreferredSize().getWidth()+45), 530, (int) registerButton.getPreferredSize().getWidth(), 40);
		registerButton.setBackground(new Color(33, 35, 39));
		registerButton.setBorder(BorderFactory.createLineBorder(new Color(33, 35, 39)));
		registerButton.setFont(new Font("serif", Font.PLAIN, 18));
		registerButton.setForeground(Color.white);
		registerButton.setFocusable(false);
		registerButton.addActionListener(this);
		
		
		//Login Label
		loginPanel.add(nachnameLabel);
		loginPanel.add(nachnameField);
		loginPanel.add(nameField);
		loginPanel.add(nameLabel);
		loginPanel.add(confirmpasswdField);
		loginPanel.add(confirmpasswd);
		loginPanel.add(registerButton);
		loginPanel.add(notRegisterd);
		loginPanel.add(loginButton);
		loginPanel.add(passwordField);
		loginPanel.add(passwordLabel);
		loginPanel.add(emailField);
		loginPanel.add(emaiLabel);
		loginPanel.add(loginLabel);
		loginPanel.add(welcomeLabel);
		loginPanel.setBounds(frameWidth/3, frameHeight/4-60, frameWidth/3, frameHeight/2+60);
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
			LoginPage loginPage = new LoginPage();
		}
		
		if (e.getSource() == loginButton) {
			Database db = new Database();
			
			String name = nameField.getText();
			String nachname = nachnameField.getText();
			String passwd = String.valueOf(((JPasswordField) passwordField).getPassword());
			String email = emailField.getText();
			String confirmPasswd = String.valueOf(((JPasswordField) confirmpasswdField).getPassword());
			
			
			if (name.isEmpty() || nachname.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Bitte fülle alle Felder aus", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				if (passwd.equals(confirmPasswd)) {
					db.insertAccount(nachname, name, passwd, email);	
					db.createTransactionTable(name, nachname);
					frame.dispose();
					LoginPage loginPage = new LoginPage();
				} else {
					JOptionPane.showMessageDialog(frame, "Passwörter sind nicht identisch", "Error", JOptionPane.ERROR_MESSAGE);
					confirmpasswdField.setText("");
					passwordField.setText("");
				}
			}
		}
	}
}
































