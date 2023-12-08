package db;

import java.awt.font.NumericShaper.Range;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.util.ArrayUtilities;

import com.mysql.cj.protocol.PacketReceivedTimeHolder;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.stream.IntStream;

public class Database {
	/*ACCOUNTS
	 * Name
	 * Vorname
	 * password
	 * kontostand
	 * 
	 */
	
	/*$Name+"transaction"
	 * Date
	 * toWho
	 * fromWho
	 * betrag
	 * 
	 */
	
	String url = "jdbc:mysql://localhost:3306/BurgiesBank";
	String user = "root";
    String pass = "";
	
    public void insertAccount(String nachname, String name, String passwd, String email) {
    	int kontostand = 1000;
    	try {
			Connection con = DriverManager.getConnection(url, user, pass);
			String insert = "INSERT INTO accounts(Vorname, Nachname, password, kontostand, email) VALUES ('" + name + "','" + nachname + "','" + passwd + "','" + kontostand + "','" + email + "');";
			Statement stm = con.createStatement();
			stm.execute(insert);
					
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
    
    public boolean loginAccount(String email, String passwd, JFrame frame) {
    	
    	boolean logged = false;
    	
		try {
			Connection con  = DriverManager.getConnection(url, user, pass);
			String get = "SELECT * FROM accounts";
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(get);
			
			
			while(res.next()){
				List<String> list = new ArrayList<String>();
				list.add(res.getString(1));
				list.add(res.getString(2));
				list.add(res.getString(3));
				list.add(res.getString(4));
				list.add(res.getString(5));
				
				if (list.contains(email) && list.contains(passwd)) {
					logged = true;
					return true;
				}
			}
			
			if (!logged) {
				JOptionPane.showMessageDialog(frame, "Email oder Passwort falsch", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return false;
	}
    
    public void createTransactionTable(String name, String lastname) {
    	String tableName = name + lastname + "transactions";
    	System.out.println(tableName);
    	
    	String sql = "CREATE TABLE "+tableName+"(toWho TEXT(100), fromWho TEXT(100), atm TEXT(100), amount INT, date DATE, balance INT);";
    	
    	try {
    		Connection con  = DriverManager.getConnection(url, user, pass);
    		Statement stm = con.createStatement();
    		stm.execute(sql);
    		
 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }
    
    public ResultSet getAccountInfo(String email) {
		ArrayList<String> account = new ArrayList<String>();
		ResultSet res = null;
		try {
			
			Connection con  = DriverManager.getConnection(url, user, pass);
			String get = "SELECT * FROM accounts WHERE email = ?";
			PreparedStatement stm = con.prepareStatement(get);
			stm.setString(1, email);
			res = stm.executeQuery();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return res;
	}
    
    public void getTransaction(String name, String nachname) {
		
	}
    
    public ResultSet getAllAccounts() {
    	ResultSet res = null;
    	
    	try {
    		Connection con  = DriverManager.getConnection(url, user, pass);
    		String get = "SELECT Vorname, Nachname FROM accounts";
    		PreparedStatement stm = con.prepareStatement(get);
    		res = stm.executeQuery();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return res;
	}
    
    public void sendMoney(String sender, String senderLastname, String receiver, String receiverLastname, String amount, int kontostand) {
		try {
			Connection con  = DriverManager.getConnection(url, user, pass);
			
			ResultSet res = getReceiverBalance(receiver);
			
			String send = "UPDATE accounts SET kontostand = kontostand + ? WHERE Vorname = ?";
			PreparedStatement stm = con.prepareStatement(send);
			stm.setString(1, amount);
			stm.setString(2, receiver);
			stm.execute();
			
			String dispose = "UPDATE accounts SET kontostand = kontostand - ? WHERE Vorname = ?";
			PreparedStatement stm2 = con.prepareStatement(dispose);
			stm2.setString(1, amount);
			stm2.setString(2, sender);
			stm2.execute();
			
			addTransaction(sender, senderLastname, receiver, receiverLastname, amount, kontostand, res, null);
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
    
    public void addTransaction(String sender, String senderLastname, String receiver, String receiverLastname, String amount, int kontostand, ResultSet res, String atm) {
		try {
			Connection con  = DriverManager.getConnection(url, user, pass);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			String date = dtf.format(now).toString();
			
			
			String receiverFullName = receiver + " " + receiverLastname;
			String senderFullName = sender + " " + senderLastname;
			
			String addTransToSender = "INSERT INTO " + sender + senderLastname + "transactions SET amount = ?,date = ?,toWho = ?, balance = ?, atm = ?";
			PreparedStatement stm = con.prepareStatement(addTransToSender);
			stm.setString(1, amount);
			stm.setString(2, date);
			stm.setString(3, receiverFullName);
			stm.setString(4, Integer.toString(kontostand-Integer.valueOf(amount)));
			stm.setString(5, atm);
			stm.execute();
			
			String senderKontostand = null;
			
			if (res.next()) {
				senderKontostand = res.getString(1);
				
				String addTransToReceiver = "INSERT INTO " + receiver + receiverLastname + "transactions SET amount = ?,date = ?,fromWho = ?, balance = ?";
				PreparedStatement stm2 = con.prepareStatement(addTransToReceiver);
				stm2.setString(1, amount);
				stm2.setString(2, date);
				stm2.setString(3, senderFullName);
				stm2.setString(4, Integer.toString(Integer.valueOf(senderKontostand) + Integer.valueOf(amount)));
				stm2.execute();
			}
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
    
    public ResultSet getReceiverBalance(String receiver) {
    	ResultSet res = null;
		try {
			
			Connection con  = DriverManager.getConnection(url, user, pass);
			String get = "SELECT kontostand FROM accounts WHERE Vorname = ?";
			PreparedStatement stm = con.prepareStatement(get);
			stm.setString(1, receiver);
			res = stm.executeQuery();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return res;
	}
    
    public ArrayList<String> getTransactionHistory(String account, String accountLastname) {
		ArrayList<String> transactionHistoryList = new ArrayList<String>();
		
		try {
			Connection con  = DriverManager.getConnection(url, user, pass);
			
			String c = "";
			String name = "";
			String date = "";
			String amount = "";
			String send = "";
			String spaces = "";
			String atm = "";
			
			String getInfo = "SELECT * FROM " + account + accountLastname + "transactions ORDER BY date";
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(getInfo);
			
			while(res.next()){
				
				if(res.getString(1) == null && res.getString(2) == null) {
					if(res.getString(3).equals("eingezahlt")) {
						System.out.println("eingezahlt test");
						date = res.getString(5);
						date = date.replace("-", "/");
						
						amount = res.getString(4);
						
						c = date + "  " + res.getString(3) + "  +" + amount;
						transactionHistoryList.add(c);
					} else {
						date = res.getString(5);
						date = date.replace("-", "/");
						
						amount = res.getString(4);
						
						c = date + "  " + res.getString(3) + "  -" + amount;
						transactionHistoryList.add(c);
					}
				} else {
					date = res.getString(5);
					date = date.replace("-", "/");
					
					if(res.getString(1) != null) {
						name = res.getString(1);
						send = "-";
					} else {
						name = res.getString(2);
						send = "+";
					}
					
					amount = res.getString(4);
					
					for(int i = 0; i < 20-name.length(); i++) {
						spaces = spaces + " ";
					}
					
					c = date + "   " + name + spaces + send + amount;
					spaces = "";
					System.out.println(c);
					transactionHistoryList.add(c);
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		Collections.reverse(transactionHistoryList);
		
		
		
		return transactionHistoryList;
	}
    
    public static <T> double[] add2BeginningOfArray(double[] nums, double i)
    {
        double[] newArray = Arrays.copyOf(nums, nums.length + 1);
        newArray[0] = i;
        System.arraycopy(nums, 0, newArray, 1, nums.length);

        return newArray;
    }
    
    
    public double[][] getChartData(String accountName) {
    	
    	try {
    		Connection con  = DriverManager.getConnection(url, user, pass);
    		String query = "SElECT balance FROM " + accountName + "transactions";
    		Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(query);
			
			DefaultXYDataset ds = new DefaultXYDataset();
			
			
			List<String> s = new ArrayList<>();
			
			while(res.next()) {
				s.add(res.getString(1));
			}
			
			int _size = s.size();

			double[] nums = new double[_size];
			double[] count = new double[_size];
			
			
			for(int i = 0; i < _size; i++) {
				nums[i] = Double.parseDouble(s.get(i));	
			}
			
			for(int i = 0; i < _size; i++) {
				count[i] = i;
			}
			
			double[] count2 = new double[_size+1];
			
			for(int i = 0; i < _size+1; i++) {
				count2[i] = i;
			}

			
			double[] nums2 = add2BeginningOfArray(nums, 1000);
			
			double[][] data = {count2, nums2};
			ds.addSeries("series1", data);
			return data;
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
    	
    }	
    
    public void deposit(String name, String lastname, String amount, int kontostand) {
    	try {
    		Connection con  = DriverManager.getConnection(url, user, pass);
    		String depositString = "UPDATE accounts SET kontostand = kontostand + ? WHERE Vorname = ?";
    		PreparedStatement stm = con.prepareStatement(depositString);
			stm.setString(1, amount);
			stm.setString(2, name);
			stm.execute();
			
			addAtmTransaction(amount, name, lastname, kontostand, true);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
    
    public void withdraw(String name, String lastname, String amount, int kontostand) {
    	try {
    		Connection con  = DriverManager.getConnection(url, user, pass);
    		String depositString = "UPDATE accounts SET kontostand = kontostand - ? WHERE Vorname = ?";
    		PreparedStatement stm = con.prepareStatement(depositString);
			stm.setString(1, amount);
			stm.setString(2, name);
			stm.execute();
			
			addAtmTransaction(amount, name, lastname, kontostand, false);
		} catch (SQLException e) {
			System.out.println(e);
		}
    }
    
    public void addAtmTransaction(String amount, String name, String lastname, int kontostand, boolean eingezahlt) {
		try {
			Connection con  = DriverManager.getConnection(url, user, pass);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			String date = dtf.format(now).toString();
			
			
			if(eingezahlt) {
				String addTransToSender = "INSERT INTO " + name + lastname + "transactions SET amount = ?,date = ?, balance = ?, atm = ?, toWho = ?, fromWho = ?";
				PreparedStatement stm = con.prepareStatement(addTransToSender);
				stm.setString(1, amount);
				stm.setString(2, date);
				stm.setString(3, Integer.toString(kontostand+Integer.valueOf(amount)));
				stm.setString(4, "eingezahlt");
				stm.setString(5, null);
				stm.setString(6, null);
				stm.execute();
			} else {
				String addTransToSender = "INSERT INTO " + name + lastname + "transactions SET amount = ?,date = ?, balance = ?, atm = ?, toWho = ?, fromWho = ?";
				PreparedStatement stm = con.prepareStatement(addTransToSender);
				stm.setString(1, amount);
				stm.setString(2, date);
				stm.setString(3, Integer.toString(kontostand-Integer.valueOf(amount)));
				stm.setString(4, "ausgezahlt");
				stm.setString(5, null);
				stm.setString(6, null);
				stm.execute();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
    }
}
