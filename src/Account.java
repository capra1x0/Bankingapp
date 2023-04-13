
public class Account {
	String name = "";
	String lastName = "";
	String password = "";
	String email = "";
	int kontostand = 0;
	
	public Account(String name, String lastName, String password, String email, int kontostand) {
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.kontostand = kontostand;
	}
}
