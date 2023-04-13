import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import db.Database;

public class InterfacePage extends JFrame implements ActionListener {

	JFrame frame = new JFrame();
	
	JPanel headerPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	
	JLabel nameLabel = new JLabel();
	JLabel lastnameLabel = new JLabel();
	JButton logoutBtn = new JButton();
	JLabel balance = new JLabel();
	JLabel balanceLabel = new JLabel();
	JLabel headerLabel = new JLabel("Insi Bank");
	JPanel transactionPanel = new JPanel();
	JLabel sendMoney = new JLabel("Geld überweisen");
	JLabel recLabel = new JLabel("an:");
	JLabel amountLabel = new JLabel("betrag:");
	JTextField amountField = new JTextField();
	JButton sendBtn = new JButton("Geld senden");
	JButton depositBtn = new JButton();
	
	JComboBox<String> names = new JComboBox<String>();
	
	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> transactionHistory = new JList<String>(model);
	
	
	int frameWidth = 1920;
	int frameHeight = 1080;
	
	Color bg = new Color(18, 0, 65);
	Color elevatedBg = new Color(40, 27, 84);
	Color headerBg = new Color(30, 99, 236);
	Color borderColor = new Color(238, 240, 244);
	Color purple = new Color(141, 122, 245);
	Color darkBg = new Color(18, 20, 22);
	Color lightBg = new Color(31, 31, 33);
	Color btnGreen = new Color(107, 235, 164);
	Color white = new Color(225, 225, 229);
	Color lighterDarkBg = new Color(45, 49, 54);
	
	static Database db = new Database();
	private ArrayList<String> account;
	
	//ArrayList<ArrayList<String>> accountsList = new ArrayList<ArrayList<String>>();
	
	InterfacePage(ArrayList<String> account) {
		this.account = account;
		
		
		ArrayList<String> accountsList = getNames();
		accountsList.remove(account.get(0));
		accountsList.remove(account.get(1));
		
		for (int i = 0; i < accountsList.size(); i = i + 2) {
			names.addItem(accountsList.get(i) + " " + accountsList.get(i+1));	
		}
		
		ArrayList<String> transactionHistoryList = db.getTransactionHistory(account.get(1), account.get(0));
		
		for (int i = 0; i < transactionHistoryList.size(); i++) {
			model.addElement(transactionHistoryList.get(i));
		}
		
		headerPanel.setBounds(0,0,frameWidth, 150);
		headerPanel.setBorder(new MatteBorder(0, 0, 2, 0, lightBg));
		headerPanel.setBackground(darkBg);
		
		headerLabel.setFont(new Font("serif", Font.BOLD, 45));
		headerLabel.setBounds((int) (frameWidth/2 - headerLabel.getPreferredSize().getWidth()/2),(int) (150/2 - headerLabel.getPreferredSize().getHeight()/2),(int) (headerLabel.getPreferredSize().getWidth()),(int) (headerLabel.getPreferredSize().getHeight()));
		headerLabel.setForeground(white);
		
		leftPanel.setBounds(0, 150,(int) (frameWidth*.15),frameHeight);
		leftPanel.setBorder(new MatteBorder(0, 0, 0, 2, lightBg));
		leftPanel.setBackground(darkBg);
		leftPanel.setLayout(null);
		
		nameLabel.setText(account.get(1));
		nameLabel.setFont(new Font("serif", Font.BOLD, 35));
		nameLabel.setBounds(20, 20, (int) (nameLabel.getPreferredSize().getWidth()), (int) (nameLabel.getPreferredSize().getHeight()));
		nameLabel.setForeground(white);
		
		lastnameLabel.setText(account.get(0));
		lastnameLabel.setFont(new Font("serif", Font.BOLD, 35));
		lastnameLabel.setBounds(20, (int) (nameLabel.getPreferredSize().getHeight() + 25), (int) (lastnameLabel.getPreferredSize().getWidth()), (int) (lastnameLabel.getPreferredSize().getHeight()));
		lastnameLabel.setForeground(white);
		
		depositBtn.setBounds(20, 700, (int) (leftPanel.getPreferredSize().getWidth()-40), 45);
		depositBtn.setFont(new Font("plain", Font.BOLD, 20));
		depositBtn.setBackground(btnGreen);
		depositBtn.setFocusable(false);
		depositBtn.setText("Einzahlen / Abheben");
		depositBtn.addActionListener(this);
		
		logoutBtn.setBounds(20, 780, (int) (leftPanel.getPreferredSize().getWidth()-40), 45);
		logoutBtn.setFont(new Font("plain", Font.BOLD, 20));
		logoutBtn.setBackground(btnGreen);
		logoutBtn.setFocusable(false);
		logoutBtn.setText("Abmelden");
		logoutBtn.addActionListener(this);
		
		balanceLabel.setText("Guthaben:");
		balanceLabel.setFont(new Font("plain", Font.BOLD, 22));
		balanceLabel.setBounds((int) (frameWidth*.15) + 50, 180, (int) (balanceLabel.getPreferredSize().getWidth()), (int) (balanceLabel.getPreferredSize().getHeight()));
		balanceLabel.setForeground(white);
		
		balance.setText(account.get(3));
		balance.setFont(new Font("plain", Font.PLAIN, 78));
		balance.setBounds((int) (frameWidth*.15) + 50, 200, (int) (balance.getPreferredSize().getWidth()), (int) (balance.getPreferredSize().getHeight()));
		balance.setForeground(white);
		
		transactionPanel.setBounds((int) (frameWidth*.15) + 50, frameHeight/2, (int) ((int) (frameWidth/2) - (frameWidth*.15)-100), frameHeight/2-100);
		transactionPanel.setBackground(Color.green);
		
		sendMoney.setFont(new Font("plain", Font.BOLD, 30));
		sendMoney.setBounds(frameWidth/2+90, frameHeight/2, (int) (sendMoney.getPreferredSize().getWidth()), (int) (sendMoney.getPreferredSize().getHeight()));
		sendMoney.setForeground(white);
		
		recLabel.setFont(new Font("plain", Font.PLAIN, 24));
		recLabel.setBounds(frameWidth/2+90, frameHeight/2+90, (int) (recLabel.getPreferredSize().getWidth()), (int) (recLabel.getPreferredSize().getHeight()));
		recLabel.setForeground(white);
		
		names.setFont(new Font("plain", Font.PLAIN, 20));
		names.setBounds((int) (frameWidth/2+90+sendMoney.getPreferredSize().getWidth()+30), frameHeight/2+90, 200, 40);
		
		amountLabel.setFont(new Font("plain", Font.PLAIN, 20));
		amountLabel.setBounds(frameWidth/2+90, frameHeight/2+180, (int) (amountLabel.getPreferredSize().getWidth()), (int) (amountLabel.getPreferredSize().getHeight()));
		amountLabel.setForeground(white);
		
		amountField.setBounds((int) (frameWidth/2+90+sendMoney.getPreferredSize().getWidth()+30), frameHeight/2+180, 200, 40);
		
		sendBtn.setFont(new Font("plain", Font.PLAIN, 20));
		sendBtn.setBounds((int) (frameWidth/2+195), frameHeight/2+270, 250, 50);
		sendBtn.setBackground(btnGreen);
		sendBtn.addActionListener(this);
		
		transactionHistory.setBounds((int) (frameWidth*.15) + 50, frameHeight/2, (int) ((int) (frameWidth/2) - (frameWidth*.15)-100), frameHeight/2-100);
		transactionHistory.setFont(new Font("plain", Font.PLAIN, 35));
		transactionHistory.setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
		transactionHistory.setBackground(lightBg);
		transactionHistory.setForeground(white);
		
		XYDataset ds = createDataset(nameLabel.getText(), lastnameLabel.getText());
		JFreeChart chart = ChartFactory.createXYLineChart("", "", "", ds, PlotOrientation.VERTICAL, false, true, false);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(frameWidth/2, 180, frameWidth/2-100, frameHeight/2-200);
		chart.setBackgroundPaint(darkBg);
		chart.getXYPlot().setDomainGridlinesVisible(true);
		chart.getXYPlot().setRangeGridlinesVisible(true);
		chart.getXYPlot().setDomainGridlinePaint(Color.black);
		chart.getXYPlot().setRangeGridlinePaint(Color.black);
		
		chart.getXYPlot().setRangeGridlinePaint(Color.gray);
		chart.getXYPlot().setDomainGridlinePaint(Color.gray);
		chart.getPlot().setBackgroundPaint(lightBg);
		chart.getXYPlot().getDomainAxis().setVisible(false);
		chart.getXYPlot().getRangeAxis().setTickLabelPaint(white);
		chart.getXYPlot().getRenderer().setSeriesPaint(0, btnGreen);
		
		NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
		xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		
		
		frame.add(chartPanel);
		frame.add(transactionHistory);
		frame.add(sendBtn);
		frame.add(amountField);
		frame.add(amountLabel);
		frame.add(recLabel);
		frame.add(names);
		frame.add(sendMoney);
//		frame.add(transactionPanel);
		frame.add(balanceLabel);
		frame.add(balance);
		leftPanel.add(logoutBtn);
		leftPanel.add(depositBtn);
		leftPanel.add(lastnameLabel);
		leftPanel.add(nameLabel);
		frame.add(leftPanel);
		frame.add(headerLabel);
		frame.add(headerPanel);
		frame.setLayout(null);
		frame.getContentPane().setBackground(darkBg);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logoutBtn) {
			LoginPage loginPage = new LoginPage();
			frame.dispose();
			
		}
		
		if (e.getSource() == depositBtn) {
			Atm atm = new Atm(account, frame);
		}
		
		
		if (e.getSource() == sendBtn) {
			String rec = names.getSelectedItem().toString();
			String amount = amountField.getText();
			System.out.println(amount);
			String sender = nameLabel.getText();
			String senderLastname = lastnameLabel.getText();
			Integer kontostand = Integer.valueOf(balance.getText());
			
			String receiver = rec.replaceAll("\\s.*", "");
			String receiverLastname = rec.substring(rec.indexOf(' ') + 1);
			//System.out.println("test1");
			
			if (amount.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Gib einen Betrag an", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				if (Integer.valueOf(balance.getText()) >= Integer.valueOf(amount)) {
					//System.out.println("send");
					db.sendMoney(sender, senderLastname, receiver, receiverLastname, amount, kontostand);
					
					ArrayList<String> account = new ArrayList<String>();
					ResultSet res = (ResultSet) db.getAccountInfo(this.account.get(4));
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
	
	public ArrayList<String> getNames () {
		//ArrayList<ArrayList<String>> accountsList = new ArrayList<ArrayList<String>>();
		ArrayList<String> containerList = new ArrayList<String>();
		ResultSet res = (ResultSet) db.getAllAccounts();
		
		try {
			while (res.next()) {
				containerList.add(res.getString(1));
				containerList.add(res.getString(2));
				//accountsList.add(containerList);
				//containerList = new ArrayList<String>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return containerList;
	}
	
	public static XYDataset createDataset(String name, String lastname) {
		DefaultXYDataset ds = new DefaultXYDataset();
		
		//double[][] data = {{.1, .2, .3}, {1, 2, 3}};
		String fullName = name + lastname;
		double[][] data = db.getChartData(fullName); 
		ds.addSeries("series1", data);
		
		return ds;
	}
	

	
}
